package com.mariabartosh;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class NameInput extends TextField
{
    private static final  TextField.TextFieldFilter textFieldFilter = new TextField.TextFieldFilter()
    {
        @Override
        public boolean acceptChar(TextField textField, char c)
        {
            return Character.isLetterOrDigit(c);
        }
    };

    public NameInput(String text, Skin skin)
    {
        super(text, skin);

        setMaxLength(15);
        setTextFieldFilter(textFieldFilter);
    }

    @Override
    public float getPrefWidth()
    {
        return super.getPrefWidth() * 2;
    }
}
