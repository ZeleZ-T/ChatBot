package co.zelez.core.command.reader.usecase;

import co.zelez.core.command.reader.entity.Param;

public class HelpReader implements IReader {
    @Override
    public String read(Param param) {
        return """
                To create new shop item use:
                shop add (Name) (Price)                         [Add one of (Name) to the list]
                shop add (name) (Price) (Quantity)              [Add (Quantity) of (Name) to the list]
                
                To add more quantity of existing item use:
                shop add (Existing name/ID)                     [Add one more of the item to the list]
                shop add (Existing name/ID) (Quantity)          [Add (Quantity) more of the item to the list]
                
                To remove shop item use:
                shop remove (Existing name/ID)                  [Remove all (Name) in list]
                shop remove (Existing name/ID) (Quantity)       [Remove (Quantity) of (Name) in list]
                
                To read shop list use:
                shop list
                shop total""";
    }
}
