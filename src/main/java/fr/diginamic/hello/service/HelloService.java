package fr.diginamic.hello.service;
import org.springframework.stereotype.Service;


@Service
public class HelloService {
    public static String salutations() {
        return "Je suis la classe de service et je vous dis Bonjour";
    }
}
