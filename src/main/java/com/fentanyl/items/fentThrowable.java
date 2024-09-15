package com.fentanyl.items;

import com.fentanyl.Fentanyl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.entity.damage.DamageSource;

public class fentThrowable extends ThrownItemEntity {
    public fentThrowable(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public fentThrowable(World world, LivingEntity owner) {
        super(Fentanyl.fentProjectileEntityType, owner, world);
    }

    public fentThrowable(World world, double x, double y, double z) {
        super(Fentanyl.fentProjectileEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.fentClump;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect) (itemStack.isEmpty() ? ParticleTypes.WHITE_ASH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); //
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 0.0f);

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0)));
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2)));
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 3, 1)));
        }
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            //this.discard(); // keeping this here just in case
        }
    }
}