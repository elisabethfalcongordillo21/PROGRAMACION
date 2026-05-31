package com.example.colonias;

import com.example.colonias.model.Colonia;
import com.example.colonias.repository.ColoniaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ColoniasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColoniasApplication.class, args);
    }

    // Carga datos de ejemplo al arrancar
    @Bean
    CommandLineRunner cargarDatos(ColoniaRepository repo) {
        return args -> {
            Colonia c1 = new Colonia();
            c1.setNombre("Bleu de Chanel");
            c1.setMarca("Chanel");
            c1.setFamiliaOlfativa("amaderado");
            c1.setVolumenMl(100);
            c1.setPrecio(135.00);
            c1.setGenero("hombre");
            repo.save(c1);

            Colonia c2 = new Colonia();
            c2.setNombre("La Vie Est Belle");
            c2.setMarca("Lancôme");
            c2.setFamiliaOlfativa("floral");
            c2.setVolumenMl(75);
            c2.setPrecio(89.90);
            c2.setGenero("mujer");
            repo.save(c2);

            Colonia c3 = new Colonia();
            c3.setNombre("CK One");
            c3.setMarca("Calvin Klein");
            c3.setFamiliaOlfativa("fresco");
            c3.setVolumenMl(200);
            c3.setPrecio(49.95);
            c3.setGenero("unisex");
            repo.save(c3);

            Colonia c4 = new Colonia();
            c4.setNombre("Black Opium");
            c4.setMarca("Yves Saint Laurent");
            c4.setFamiliaOlfativa("oriental");
            c4.setVolumenMl(50);
            c4.setPrecio(79.00);
            c4.setGenero("mujer");
            repo.save(c4);

            Colonia c5 = new Colonia();
            c5.setNombre("Acqua di Gio");
            c5.setMarca("Giorgio Armani");
            c5.setFamiliaOlfativa("cítrico");
            c5.setVolumenMl(100);
            c5.setPrecio(95.00);
            c5.setGenero("hombre");
            repo.save(c5);
        };
    }
}
