package pers.towdium.justEnoughCalculation.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pers.towdium.justEnoughCalculation.gui.calculator.ContainerCalculator;
import pers.towdium.justEnoughCalculation.gui.calculator.GuiCalculator;
import pers.towdium.justEnoughCalculation.gui.itemPicker.ContainerItemPicker;
import pers.towdium.justEnoughCalculation.gui.itemPicker.GuiItemPicker;
import pers.towdium.justEnoughCalculation.gui.recipeEditor.ContainerRecipeEditor;
import pers.towdium.justEnoughCalculation.gui.recipeEditor.GuiRecipeEditor;

/**
 * @author Towdium
 */
public class GuiHandler implements IGuiHandler {
    public static final class GuiId{
        public static final int CALCULATOR = 0;
        public static final int ITEM_PICKER = 1;
        public static final int RECIPE_EDITOR = 2;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GuiId.CALCULATOR: return new GuiCalculator(new ContainerCalculator(player, player.getHeldItem()));
            case GuiId.ITEM_PICKER: return new GuiItemPicker(new ContainerItemPicker());
            case GuiId.RECIPE_EDITOR: return new GuiRecipeEditor(new ContainerRecipeEditor(player));
        }
        return null;
    }
}