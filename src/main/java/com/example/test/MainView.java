package com.example.test;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("main")
public class MainView extends VerticalLayout {
    private Value curValue;

    public MainView(ValueRepository valueRepository) {
        TextField textField = new TextField("Current value:");
        if (valueRepository.findById(1).isPresent())
            curValue = valueRepository.findById(1).get();
        else {
            curValue = new Value(0);
            valueRepository.save(curValue);
        }
        textField.setValue(curValue.toString());


        Binder<Value> binder = new Binder<>(Value.class);
        binder.forField(textField)
                .withValidator(text -> text
                        .replace(" ", "")
                        .chars()
                        .filter(ch -> (ch >= '0' && ch <= '9'))
                        .count() == text.length(), "Not a number")
                .bind(Value::toString, Value::setVal);

        binder.addValueChangeListener((HasValue.ValueChangeListener<HasValue.ValueChangeEvent<?>>) valueChangeEvent -> {
            if (binder.validate().isOk()) {
                curValue = binder.getBean();
                valueRepository.save(curValue);
            }
        });


        Button button = new Button("Increment");
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            binder.setBean(curValue.increment());
            valueRepository.save(curValue);
        });


        add(textField, button);
    }
}