/*
 * Requiem
 * Copyright (C) 2017-2021 Ladysnake
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses>.
 *
 * Linking this mod statically or dynamically with other
 * modules is making a combined work based on this mod.
 * Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 * In addition, as a special exception, the copyright holders of
 * this mod give you permission to combine this mod
 * with free software programs or libraries that are released under the GNU LGPL
 * and with code included in the standard release of Minecraft under All Rights Reserved (or
 * modified versions of such code, with unchanged license).
 * You may copy and distribute such a system following the terms of the GNU GPL for this mod
 * and the licenses of the other code concerned.
 *
 * Note that people who make modified versions of this mod are not obligated to grant
 * this special exception for their modified versions; it is their choice whether to do so.
 * The GNU General Public License gives permission to release a modified version without this exception;
 * this exception also makes it possible to release a modified version which carries forward this exception.
 */
package ladysnake.pandemonium;

import ladysnake.pandemonium.common.PandemoniumConfig;
import ladysnake.pandemonium.common.remnant.PandemoniumRemnantTypes;
import ladysnake.requiem.Requiem;
import ladysnake.requiem.api.v1.RequiemPlugin;
import ladysnake.requiem.api.v1.dialogue.DialogueRegistry;
import ladysnake.requiem.api.v1.event.requiem.PossessionStartCallback;
import ladysnake.requiem.api.v1.remnant.RemnantType;
import ladysnake.requiem.common.VanillaRequiemPlugin;
import ladysnake.requiem.common.dialogue.PlayerDialogueTracker;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PandemoniumRequiemPlugin implements RequiemPlugin {

    @Override
    public void onRequiemInitialize() {
        if (PandemoniumConfig.possession.allowPossessingAllMobs) {
            // Enderman specific behaviour is unneeded now that players can possess them
            PossessionStartCallback.EVENT.unregister(new Identifier(Requiem.MOD_ID, "enderman"));
            PossessionStartCallback.EVENT.register(Pandemonium.id("allow_everything"), (target, possessor, simulate) -> PossessionStartCallback.Result.ALLOW);
        }
    }

    @Override
    public void registerDialogueActions(DialogueRegistry registry) {
        registry.registerAction(PlayerDialogueTracker.BECOME_WANDERING_SPIRIT, p -> VanillaRequiemPlugin.handleRemnantChoiceAction(p, PandemoniumRemnantTypes.WANDERING_SPIRIT));
    }

    @Override
    public void registerRemnantStates(Registry<RemnantType> registry) {
        Registry.register(registry, Pandemonium.id("wandering_spirit"), PandemoniumRemnantTypes.WANDERING_SPIRIT);
    }
}
