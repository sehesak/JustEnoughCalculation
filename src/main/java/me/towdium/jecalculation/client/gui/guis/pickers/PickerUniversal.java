package me.towdium.jecalculation.client.gui.guis.pickers;

import mcp.MethodsReturnNonnullByDefault;
import me.towdium.jecalculation.client.gui.IWPicker;
import me.towdium.jecalculation.client.gui.JecGui;
import me.towdium.jecalculation.client.gui.Resource;
import me.towdium.jecalculation.client.gui.drawables.*;
import me.towdium.jecalculation.data.label.labels.LabelUniversal;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

/**
 * Author: towdium
 * Date:   17-9-29.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SideOnly(Side.CLIENT)
public class PickerUniversal extends IWPicker.Simple {
    public PickerUniversal() {
        WLabelScroll scroll = new WLabelScroll(7, 69, 8, 5, WLabel.enumMode.PICKER, true).setLabels(new ArrayList<>());
        WTextField search = new WTextField(25, 45, 90);
        WTextField create = new WTextField(25, 7, 70);
        create.setLsnrText(s -> create.setColor(s.equals("") ? JecGui.COLOR_TEXT_RED : JecGui.COLOR_TEXT_WHITE));
        add(new WSearch(l -> callback.value.accept(l), search, scroll));
        add(new WIcon(149, 45, 20, 20, Resource.ICN_HELP_N, Resource.ICN_HELP_F, "picker_universal.help_search"));
        add(new WIcon(149, 7, 20, 20, Resource.ICN_HELP_N, Resource.ICN_HELP_F, "picker_universal.help_create"));
        add(new WIcon(7, 45, 20, 20, Resource.ICN_TEXT_N, Resource.ICN_TEXT_F, "picker_universal.text_search"));
        add(new WIcon(7, 7, 20, 20, Resource.ICN_TEXT_N, Resource.ICN_TEXT_F, "picker_universal.text_create"));
        add(new WLine(36));
        add(new WButtonIcon(95, 7, 20, 20, Resource.BTN_YES_N, Resource.BTN_YES_F).setListenerLeft(() -> {
            if (!create.getText().equals("")) callback.value.accept(new LabelUniversal(create.getText(), 1));
        }));
        add(create);

    }
}
