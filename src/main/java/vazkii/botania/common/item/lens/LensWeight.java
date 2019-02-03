/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Jan 24, 2015, 4:43:16 PM (GMT)]
 */
package vazkii.botania.common.item.lens;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.WorldServer;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.core.handler.ConfigHandler;

import java.util.Map;

public class LensWeight extends Lens {
	@Override
	public boolean collideBurst(IManaBurst burst, EntityThrowable entity, RayTraceResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
		if(!entity.world.isRemote && !burst.isFake() && pos.getBlockPos() != null) {
			int harvestLevel = ConfigHandler.harvestLevelWeight;

			BlockPos bPos = pos.getBlockPos();
			Block block = entity.world.getBlockState(bPos).getBlock();
			IBlockState state = entity.world.getBlockState(bPos);
			int neededHarvestLevel = block.getHarvestLevel(state);

			if(entity.world.isAirBlock(bPos.down()) && state.getBlockHardness(entity.world, bPos) != -1 && neededHarvestLevel <= harvestLevel && entity.world.getTileEntity(bPos) == null && state.canSilkHarvest(entity.world, bPos, null)) {
				EntityFallingBlock falling = new EntityFallingBlock(entity.world, bPos.getX() + 0.5, bPos.getY(), bPos.getZ() + 0.5, state);
				falling.fallTime = 1;
				entity.world.removeBlock(bPos);
				((WorldServer) entity.world).spawnParticle(new BlockParticleData(Particles.FALLING_DUST, state), bPos.getX() + 0.5, bPos.getY() + 0.5, bPos.getZ() + 0.5, 10, 0.45, 0.45, 0.45, 5);
				entity.world.spawnEntity(falling);
			}
		}

		return dead;
	}

}
