package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.gitlab.gn5r.boot.commons.generator.FQCNBeanNameGenerator;

@SpringBootApplication
@ComponentScan(nameGenerator = FQCNBeanNameGenerator.class)
public class ${name}Application {

	public static void main(String[] args) {
		SpringApplication.run(${name}Application.class, args);
	}

}
