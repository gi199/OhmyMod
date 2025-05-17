package com.gi199.randomod.criterion;

import com.gi199.randomod.RandoMod;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static final UseToolCriterion USE_TOOL = Criteria.register(RandoMod.MOD_ID + ":use_tool", new UseToolCriterion());

    public static void init() {
        //public static final ParameterizedUseToolCriterion PARAMETERIZED_USE_TOOL = Criteria.register(RandoMod.MOD_ID + ":parameterized_use_tool", new ParameterizedUseToolCriterion());
    }

    /*
     * TODO:ParameterizedUseToolCriterion修复
     */
}