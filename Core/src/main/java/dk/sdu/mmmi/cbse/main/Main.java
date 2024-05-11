package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ModuleConfig.class);

        System.out.println("BEAN COUNT: " + annotationConfigApplicationContext.getBeanDefinitionCount() + "\n");
        for (String bean : annotationConfigApplicationContext.getBeanDefinitionNames()) {
            System.out.println("Bean: " + bean);
        }

        Game game = annotationConfigApplicationContext.getBean(Game.class);

        game.start(window);
        game.render();
    }
}
