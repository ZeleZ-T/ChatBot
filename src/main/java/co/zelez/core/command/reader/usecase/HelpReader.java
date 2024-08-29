package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;
import lombok.Generated;

import java.util.Arrays;
import java.util.List;

public class HelpReader {
    private HelpReader() {}

    @Generated
    public static String help() {
        List<String> emojis = Arrays.asList("\uD83C\uDD95", "\u2935", "\u267B", "\uD83D\uDCCB", "\uD83D\uDD03");
        return String.format("""
                %s To create new shop item use:
                add / a (Name) (Price)
                add / a (name) (Price) (Quantity)
                
                %s To add more quantity of existing item use:
                add / a (Existing name / ID)
                add / a (Existing name/ ID) (Quantity)
              
                %s To remove shop item use:
                remove / r (Existing name / ID)
                remove / r (Existing name / ID) (Quantity)
                
                %s  To read actual shop list use:
                list / l
                total / t
                
                %s To clear actual shop list use:
                clear / c
                """, emojis.get(0), emojis.get(1), emojis.get(2), emojis.get(3), emojis.get(4));
    }
}
