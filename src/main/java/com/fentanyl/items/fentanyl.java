package com.fentanyl.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.core.jmx.Server;


public class fentanyl extends Item {

    public fentanyl(Settings settings) {
        super(settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 5*20, 0));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 5*20, 0));
            BlockPos frontOfPlayer = attacker.getBlockPos().offset(attacker.getHorizontalFacing(), 2);
            //Vec3d Pos = attacker.getEyePos().offset(attacker.getHorizontalFacing(), attacker.getEyePos().distanceTo(target.getEyePos())/2);
            Vec3d Pos = attacker.getEyePos().lerp(target.getEyePos(), 0.3f);
            if(target.getWorld() instanceof ServerWorld){
                ((ServerWorld)target.getWorld()).spawnParticles(ParticleTypes.SNOWFLAKE,
                        Pos.getX(),
                        Pos.getY(),
                        Pos.getZ(),
                        20,
                        0,
                        0,
                        0,
                        0);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
