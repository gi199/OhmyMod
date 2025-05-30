package com.gi199.randomod.optimiz;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Box;
import net.minecraft.client.render.Frustum;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 优化渲染类
 * 1. 线程安全的可见区块集合
 * 2. 异步遮挡剔除
 * 3. 动态分辨率/LOD
 * 4. 批量渲染任务调度
 * 5. 优先级渲染任务
 * 6. 线程池管理
 * 7. 事件总线集成
 */

public class BlockRenderOptimiz {
    private static final Set<ChunkPos> visibleChunks = ConcurrentHashMap.newKeySet();
    private static final Set<BlockPos> occludedBlocks = ConcurrentHashMap.newKeySet();
    private static final Set<BlockPos> transparentBlocks = ConcurrentHashMap.newKeySet();
    private static final ConcurrentHashMap<BlockPos, Boolean> renderCache = new ConcurrentHashMap<>();
    private static final PriorityBlockingQueue<RenderTask> taskQueue = new PriorityBlockingQueue<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors()));
    private static volatile Frustum frustum;
    private static volatile double renderScale = 1.0;
    private static final double LOD_DIST = 128.0;

    /**
     * 更新可见区块
     */
    public static void updateVisibleChunks(Set<ChunkPos> chunks) {
        visibleChunks.clear();
        visibleChunks.addAll(chunks);
        renderCache.clear();
        occludedBlocks.clear();
    }

    /** 更新视锥体 */
    public static void updateFrustum(Frustum f) {
        frustum = f;
    }

    /** 设置渲染分辨率缩放 */
    public static void setRenderScale(double scale) {
        renderScale = Math.max(0.5, Math.min(1.0, scale));
    }

    /** 标记透明方块 */
    public static void markTransparent(BlockPos pos) {
        transparentBlocks.add(pos);
    }

    /** 遮挡剔除（异步） */
    public static void performOcclusionCulling(List<BlockPos> blocks, Predicate<BlockPos> occlusionTest) {
        executor.submit(() -> {
            for (BlockPos pos : blocks) {
                if (occlusionTest.test(pos)) occludedBlocks.add(pos);
            }
        });
    }

    /** 判断方块是否需要渲染 */
    public static boolean shouldRenderBlock(BlockPos pos, Box box) {
        if (!visibleChunks.contains(new ChunkPos(pos))) return false;
        if (frustum != null && !frustum.isVisible(box)) return false;
        if (transparentBlocks.contains(pos)) return false;
        if (occludedBlocks.contains(pos)) return false;
        if (renderScale < 1.0 && !isNearPlayer(pos)) return false;
        return !isFarForLOD(pos);
    }

    /** 是否靠近玩家（动态分辨率/LOD） */
    private static boolean isNearPlayer(BlockPos pos) {
        var player = net.minecraft.client.MinecraftClient.getInstance().player;
        if (player == null) return true;
        double dist = player.getPos().squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        return dist < 256 * renderScale;
    }

    /** LOD判定 */
    private static boolean isFarForLOD(BlockPos pos) {
        var player = net.minecraft.client.MinecraftClient.getInstance().player;
        if (player == null) return false;
        double dist = player.getPos().squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        return dist > LOD_DIST * LOD_DIST;
    }

    /** 批量多线程过滤可渲染方块 */
    public static Future<List<BlockPos>> filterRenderableBlocksAsync(List<BlockPos> blocks, Map<BlockPos, Box> boxMap) {
        return executor.submit(() -> {
            List<BlockPos> result = new ArrayList<>();
            for (BlockPos pos : blocks) {
                Boolean cached = renderCache.get(pos);
                boolean render = cached != null ? cached : shouldRenderBlock(pos, boxMap.get(pos));
                renderCache.put(pos, render);
                if (render) result.add(pos);
            }
            return result;
        });
    }

    /** 添加渲染任务（带优先级） */
    public static void scheduleRenderTask(BlockPos pos, int priority) {
        taskQueue.add(new RenderTask(pos, priority));
    }

    /** 批量处理渲染任务（分区调度+批处理） */
    public static void processTasks(Map<BlockPos, Box> boxMap, int batchSize, Consumer<List<BlockPos>> renderBatch) {
        executor.submit(() -> {
            List<BlockPos> batch = new ArrayList<>(batchSize);
            while (!taskQueue.isEmpty()) {
                RenderTask task = taskQueue.poll();
                if (task != null && shouldRenderBlock(task.blockPos(), boxMap.get(task.blockPos()))) {
                    batch.add(task.blockPos());
                    if (batch.size() >= batchSize) {
                        renderBatch.accept(batch);
                        batch.clear();
                    }
                }
            }
            if (!batch.isEmpty()) renderBatch.accept(batch);
        });
    }

    /**
     * 关闭线程池，释放资源
     */
    public static void shutdown() {
        executor.shutdown();
    }

    /**
     * 渲染任务类（优先级）
     */
        private record RenderTask(BlockPos blockPos, int priority) implements Comparable<RenderTask> {
        @Override
            public int compareTo(RenderTask other) {
                return Integer.compare(other.priority, this.priority);
            }
        }
    public static void initialize() {
        // 示例：清理所有缓存，准备初始状态
        visibleChunks.clear();
        occludedBlocks.clear();
        transparentBlocks.clear();
        renderCache.clear();
        taskQueue.clear();
        updateFrustum(null);
        updateVisibleChunks(new HashSet<>());
        // 如有事件总线，可在此注册监听器
        //EventBus.register(new BlockRenderEventHandler());
        shutdown();
    }
}