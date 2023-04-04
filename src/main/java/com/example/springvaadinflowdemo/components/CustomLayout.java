package com.example.springvaadinflowdemo.components;

import com.example.springvaadinflowdemo.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class CustomLayout extends AppLayout {

    private final transient SecurityService securityService;

    private final Button themeToggleBtn = new Button();

    public CustomLayout(SecurityService securityService) {
        this.securityService = securityService;

        getElement().setAttribute("theme", "dark");

        addHeader();
        configureDrawer();
    }

    private void addHeader() {
        H1 title = new H1("Spring Vaadin Flow Demo");
        title.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        themeToggleBtn.addClickListener(e -> toggleTheme());
        themeToggleBtn.setIcon(new Icon(VaadinIcon.MOON));
        themeToggleBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button logout = new Button("Log out", e -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var header = new HorizontalLayout(new DrawerToggle(), title, themeToggleBtn, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void configureDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Dashboard", DashboardView.class),
                new RouterLink("Customers", CustomerListView.class)
        ));
    }

    private void toggleTheme() {
        if (getElement().getAttribute("theme").equalsIgnoreCase("dark")) {
            themeToggleBtn.setIcon(new Icon(VaadinIcon.SUN_O));
            themeToggleBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            getElement().setAttribute("theme", "light");
        } else {
            themeToggleBtn.setIcon(new Icon(VaadinIcon.MOON));
            themeToggleBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            getElement().setAttribute("theme", "dark");
        }
    }

}
