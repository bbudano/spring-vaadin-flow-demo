package com.example.springvaadinflowdemo.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = CustomLayout.class)
@PageTitle("Dashboard | Vaadin Flow Demo")
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getWelcomeTitle());
    }

    private Component getWelcomeTitle() {
        H1 welcome = new H1("Welcome to Spring Vaadin Flow Demo");
        welcome.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        return welcome;
    }

}
