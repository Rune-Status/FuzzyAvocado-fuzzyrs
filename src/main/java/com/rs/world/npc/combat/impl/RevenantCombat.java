package com.rs.world.npc.combat.impl;

import com.rs.core.utils.Utils;
import com.rs.player.Player;
import com.rs.world.Animation;
import com.rs.world.Entity;
import com.rs.world.World;
import com.rs.world.npc.NPC;
import com.rs.world.npc.combat.CombatScript;
import com.rs.world.npc.combat.NPCCombatDefinitions;

public class RevenantCombat extends CombatScript {

    @Override
    public Object[] getKeys() {
        return new Object[]{13465, 13466, 13467, 13468, 13469, 13470, 13471,
                13472, 13473, 13474, 13475, 13476, 13477, 13478, 13479, 13480,
                13481};
    }

    public int getMagicAnimation(final NPC npc) {
        switch (npc.getId()) {
            case 13465:
                return 7500;
            case 13466:
            case 13467:
            case 13468:
            case 13469:
                return 7499;
            case 13470:
            case 13471:
                return 7506;
            case 13472:
                return 7503;
            case 13473:
                return 7507;
            case 13474:
                return 7496;
            case 13475:
                return 7497;
            case 13476:
                return 7515;
            case 13477:
                return 7498;
            case 13478:
                return 7505;
            case 13479:
                return 7515;
            case 13480:
                return 7508;
            case 13481:
            default:
                // melee emote, better than 0
                return npc.getCombatDefinitions().getAttackEmote();
        }
    }

    public int getRangeAnimation(final NPC npc) {
        switch (npc.getId()) {
            case 13465:
                return 7501;
            case 13466:
            case 13467:
            case 13468:
            case 13469:
                return 7513;
            case 13470:
            case 13471:
                return 7519;
            case 13472:
                return 7516;
            case 13473:
                return 7520;
            case 13474:
                return 7521;
            case 13475:
                return 7510;
            case 13476:
                return 7501;
            case 13477:
                return 7512;
            case 13478:
                return 7518;
            case 13479:
                return 7514;
            case 13480:
                return 7522;
            case 13481:
            default:
                // melee emote, better than 0
                return npc.getCombatDefinitions().getAttackEmote();
        }
    }

    @Override
    public int attack(final NPC npc, final Entity target) {
        final NPCCombatDefinitions defs = npc.getCombatDefinitions();
        if (npc.getHitpoints() < npc.getMaxHitpoints() / 2
                && Utils.random(5) == 0) {
            npc.heal(100);
        }

        int attackStyle = Utils.random(3);
        if (attackStyle == 2) { // checks if can melee
            final int distanceX = target.getX() - npc.getX();
            final int distanceY = target.getY() - npc.getY();
            final int size = npc.getSize();
            if ((distanceX > size || distanceX < -1 || distanceY > size || distanceY < -1)) {
                attackStyle = Utils.random(2);
            }
        }
        if (attackStyle != 2 && target instanceof Player) {
            final Player targetPlayer = (Player) target;
            targetPlayer.getPackets().sendSound(202, 0, 1);
        }
        switch (attackStyle) {
            case 0: // magic
                final int damage = CombatScript.getRandomMaxHit(npc, defs.getMaxHit(),
                        NPCCombatDefinitions.MAGE, target);
                CombatScript.delayHit(npc, 2, target, CombatScript.getMagicHit(npc, damage));
                World.sendProjectile(npc, target, 1276, 34, 16, 30, 35, 16, 0);
                npc.setNextAnimation(new Animation(getMagicAnimation(npc)));
                break;
            case 1: // range
                CombatScript.delayHit(
                        npc,
                        2,
                        target,
                        CombatScript.getRangeHit(
                                npc,
                                CombatScript.getRandomMaxHit(npc, defs.getMaxHit(),
                                        NPCCombatDefinitions.RANGE, target)));
                World.sendProjectile(npc, target, 1278, 34, 16, 30, 35, 16, 0);
                npc.setNextAnimation(new Animation(getRangeAnimation(npc)));
                break;
            case 2: // melee
                CombatScript.delayHit(
                        npc,
                        0,
                        target,
                        CombatScript.getMeleeHit(
                                npc,
                                CombatScript.getRandomMaxHit(npc, defs.getMaxHit(),
                                        NPCCombatDefinitions.MELEE, target)));
                npc.setNextAnimation(new Animation(defs.getAttackEmote()));
                break;
        }
        return defs.getAttackDelay();
    }
}