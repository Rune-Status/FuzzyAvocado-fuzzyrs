package com.rs.content.dialogues.impl;

import com.rs.content.dialogues.Dialogue;
import com.rs.world.npc.qbd.QueenBlackDragon;

/**
 * Handles the Queen Black Dragon reward chest dialogue.
 *
 * @author Emperor
 */
public final class RewardChest extends Dialogue {

    /**
     * The NPC.
     */
    private QueenBlackDragon npc;

    @Override
    public void start() {
        npc = (QueenBlackDragon) parameters[0];
        super.sendDialogue(
                "This strange device is covered in indecipherable script. It opens for you,",
                "displaying only a small sample of the objects it contains.");

    }

    @Override
    public void run(final int interfaceId, final int componentId) {
        npc.openRewardChest(true);
        super.end();
    }

    @Override
    public void finish() {
    }

}