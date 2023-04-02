package com.example.springvaadinflowdemo.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class CustomLayout extends AppLayout {

    public CustomLayout() {
        addHeader();
        configureDrawer();
    }

    private void addHeader() {
        H1 title = new H1("Spring Vaadin Flow Demo");
        title.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), title );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void configureDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Dashboard", DashboardView.class),
                new RouterLink("Customers", CustomerListView.class)
        ));
    }

}
