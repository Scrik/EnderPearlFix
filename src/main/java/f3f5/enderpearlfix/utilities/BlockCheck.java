package f3f5.enderpearlfix.utilities;

import org.bukkit.block.Block;
import org.bukkit.material.Openable;

@SuppressWarnings("deprecation")
public class BlockCheck
{
    public boolean isSafe;
    public double adjustY;

    public BlockCheck (Block block) {
        isSafe=false; adjustY=0; Block above = block.getLocation().clone().add(0, 1, 0).getBlock(); Block below = block.getLocation().clone().add(0, -1, 0).getBlock(); Block upTwo = block.getLocation().clone().add(0, 2, 0).getBlock(); Block downTwo = block.getLocation().clone().add(0, -2, 0).getBlock();
        if (isSafe(block) && isSafe(above)) { isSafe=true; }
        else if (isSafe(block) && isSafe(below)) { isSafe=true; adjustY=-1.0f; }
        else if (isSafe(block)) {
            if (!isSolid(above) && (isOpenDoor(above) || isTopDoor(above))) { isSafe=true; }
            else if (!isSolid(below) && ((isTrapDoor(below) && !isTopDoor(below)) || isOpenDoor(below))) { isSafe=true; adjustY=-0.8125f; }
            else if (!isSolid(above) && !isSolid(below) && isTopSlab(above) && (isSlab(below) && !isTopSlab(below))) { isSafe=true; adjustY=-0.5f; }
        }
        else if (isTrapDoor(block) && !isTopDoor(block) && isSafe(above)) { isSafe=true; adjustY=0.1875f; }
        else if (isTopDoor(block) && isSafe(below)) { isSafe=true; adjustY=-1.0f; }
        else if (isSlab(block) && !isTopSlab(block) && isSafe(above)) {
            if (isSafe(upTwo)) { isSafe=true; }
            else if (isTopSlab(upTwo) || isTopDoor(upTwo)) { isSafe=true; }
            if (isSafe) { adjustY=.5f; }
        }
        else if (isTopSlab(block) && isSafe(below)) {
            if (isSafe(downTwo)) { isSafe=true; adjustY=-2.0f; }
            else if (isSlab(downTwo) && !isTopSlab(downTwo)) { isSafe=true; adjustY=-1.5f; }
            else if (isTrapDoor(downTwo) && !isTopDoor(downTwo)) { isSafe=true; adjustY=-1.8125f; }
        }
    }
    public boolean isSolid(Block block)
    {
        int type = block.getType().getId();
        if (type == 355) {
          return true;
        } else {
          return false;
        }
    }
    private boolean isSlab(Block block) { return block.getType().getId() == 44 || block.getType().getId() == 126 || block.getType().getId() == 182; }
    private boolean isTrapDoor(Block block) { return block.getType().getId() == 96 || block.getType().getId() == 167; }
    private boolean isOpenDoor(Block block) { return (isTrapDoor(block) && ((Openable)block.getState().getData()).isOpen()); }
    private boolean isTopDoor(Block block) { if (!isTrapDoor(block)) { return false; } return block.getData() == 8 || block.getData() == 9 || block.getData() == 10 || block.getData() == 11; }
    private boolean isTopSlab(Block block) { if (!isSlab(block)) { return false; } return block.getData() == 8 || block.getData() == 9 || block.getData() == 10 || block.getData() == 11 || block.getData() == 12 || block.getData() == 13 || block.getData() == 14 || block.getData() == 15; }
    private boolean isSafe(Block block) { return !isSolid(block) && !isSlab(block) && (!isTrapDoor(block) || isOpenDoor(block)); }
}
