package net.github.dctime.libs;

import net.minecraft.network.chat.FormattedText;

public interface FormattedTextGetterSetter {
    public FormattedText[] getFormattedText();
    public void setFormattedText(int index, String text);
}
