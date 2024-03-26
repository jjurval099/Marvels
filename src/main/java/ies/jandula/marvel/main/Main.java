package ies.jandula.marvel.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import ies.jandula.marvel.exceptions.MarvelExceptions;

public class Main {
    public static void main(String[] args) throws MarvelExceptions {
        String archivo = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "marvelHeroes.csv";

        File file = new File(archivo);

        Scanner scanner = null;

        try {
            scanner = new Scanner(file);

            Map<String, List<String>> mapaSuperheroes = new TreeMap<>();

            // Salto de la primera línea que contiene los encabezados
            scanner.nextLine(); 

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                // Verificar si la línea no está vacía y no contiene solo espacios en blanco
                if (!linea.trim().isEmpty()) {
                    String[] campos = linea.split(","); 
                
                    String nombreSuperheroe = campos[0].trim(); 
                    
                    List<String> poderes = new ArrayList<>(); 

                    // Agregar los poderes del superhéroe a la lista
                    for (int i = 1; i < campos.length; i++) {
                        poderes.add(campos[i].trim());
                    }

                    // Agregar el superhéroe y sus poderes al mapa
                    mapaSuperheroes.put(nombreSuperheroe, poderes);                        
                    
                }
            }

            // Crear un TreeMap para almacenar los superhéroes con mas poderes
            TreeMap<Integer, String> superHeroesMasPoder = new TreeMap<>(new Comparator<Integer>() 
            {
                @Override
                public int compare(Integer poderes1, Integer poderes2) {
                    return Integer.compare(poderes2, poderes1); // Orden de mayor a menor
                }
            });

            // Crear un TreeMap para almacenar los superhéroes con menos poderes
            TreeMap<Integer, String> superHeroesMenosPoder = new TreeMap<>();

            // Calcular el número de poderes de cada superhéroe y almacenarlo en los TreeMap
            for (Map.Entry<String, List<String>> entry : mapaSuperheroes.entrySet()) 
            {
                int contadorPoderes = 0;
                
                List<String> poderes = entry.getValue();
                
                for (String poder : poderes) 
                {
                    if (poder.equalsIgnoreCase("true")) 
                    {
                        contadorPoderes++;
                    }
                }
                superHeroesMasPoder.put(contadorPoderes, entry.getKey());
                superHeroesMenosPoder.put(contadorPoderes, entry.getKey());
            }

            // Imprimir los tres superhéroes con más poderes
            int contador = 0;
            System.out.println("Superhéroes con más poderes:");
            for (Map.Entry<Integer, String> entry : superHeroesMasPoder.entrySet()) 
            {
                if (contador < 3) 
                {
                    System.out.println(entry.getValue() + " (" + entry.getKey() + " poderes)");
                    contador++;
                } 
            }

            // Imprimir los tres superhéroes con menos poderes
            contador = 0;
            
            System.out.println("\nSuperhéroes con menos poderes:");
            for (Map.Entry<Integer, String> entry : superHeroesMenosPoder.entrySet()) 
            {
                if (contador < 3) 
                {
                    System.out.println(entry.getValue() + " (" + entry.getKey() + " poderes)");
                    contador++;
                } 
            }
            
            // Crear un mapa para almacenar la cantidad de superhéroes por cada superpoder
            Map<String, Integer> poderesContados = new HashMap<>();

            // Contar la cantidad de superhéroes que tienen cada superpoder
            for (List<String> poderes : mapaSuperheroes.values()) {
                for (String poder : poderes) {
                    poderesContados.put(poder, poderesContados.getOrDefault(poder, 0) + 1);
                }
            }

         // Crear un comparador personalizado que ordene de forma inversa
            Comparator<Integer> reverseComparator = new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            };

            // Crear TreeMap con el comparador personalizado
            TreeMap<Integer, String> superPoderesMasComunes = new TreeMap<>(reverseComparator);

            // Llenar el TreeMap con la cantidad de superhéroes por cada superpoder
            for (Map.Entry<String, Integer> entry : poderesContados.entrySet()) {
                superPoderesMasComunes.put(entry.getValue(), entry.getKey());
            }

            // Imprimir los tres superpoderes más comunes
            System.out.println("Superpoderes más comunes:");
            int contadorPoderes = 0;
            for (Map.Entry<Integer, String> entry : superPoderesMasComunes.entrySet()) {
                if (contadorPoderes < 3) {
                    System.out.println(entry.getValue() + " (" + entry.getKey() + " superhéroes)");
                    contadorPoderes++;
                }
            }

            // Crear un TreeMap para almacenar los superpoderes ordenados por la cantidad de superhéroes (de menor a mayor)
            TreeMap<Integer, String> superPoderesMenosComunes = new TreeMap<>();

            // Llenar el TreeMap con la cantidad de superhéroes por cada superpoder
            for (Map.Entry<String, Integer> entry : poderesContados.entrySet()) {
                superPoderesMenosComunes.put(entry.getValue(), entry.getKey());
            }

            // Imprimir los tres superpoderes menos comunes
            System.out.println("\nSuperpoderes menos comunes:");
            contadorPoderes = 0;
            for (Map.Entry<Integer, String> entry : superPoderesMenosComunes.entrySet()) {
                if (contadorPoderes < 3) {
                    System.out.println(entry.getValue() + " (" + entry.getKey() + " superhéroes)");
                    contadorPoderes++;
                }
            }

        } 
        catch (FileNotFoundException fileNotFoundException)
        {
            String error = "File not found";
            System.out.println(fileNotFoundException.getMessage());
            throw new MarvelExceptions(1, error); 
        } 
        finally
        {
            if (scanner != null)
            {
                scanner.close();
            }
        }
    }
}
