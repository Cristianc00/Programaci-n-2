import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Pregunta {
    private String enunciado;
    private String[] opciones;
    private int respuestaCorrecta; // Índice de 0 a 3 (0=A, 1=B, 2=C, 3=D)
    private String categoria;

    public Pregunta(String enunciado, String[] opciones, int respuestaCorrecta, String categoria) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
    }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String[] getOpciones() { return opciones; }
    public void setOpciones(String[] opciones) { this.opciones = opciones; }

    public int getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(int respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void mostrarPregunta() {
        System.out.println("Categoría: " + categoria);
        System.out.println("Pregunta: " + enunciado);
        char letra = 'A';
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("  " + letra + ") " + opciones[i]);
            letra++;
        }
    }
}

public class Main {
    private static ArrayList<Pregunta> baseDeDatos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        precargarPreguntasEjemplo();
        int opcion = 0;

        do {
            System.out.println("\n========================================");
            System.out.println("   PLATAFORMA DE CUESTIONARIOS EN LÍNEA");
            System.out.println("========================================");
            System.out.println("1. Modo Creador (Gestionar Preguntas)");
            System.out.println("2. Modo Estudiante (Responder Cuestionario)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
            } else {
                scanner.nextLine();
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    menuCreador();
                    break;
                case 2:
                    menuEstudiante();
                    break;
                case 3:
                    System.out.println("\n¡Saliendo del programa! ¡Mucho éxito con tu práctica!");
                    break;
                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    private static void precargarPreguntasEjemplo() {
        baseDeDatos.add(new Pregunta(
            "¿Qué palabra clave se usa en Java para definir una constante?",
            new String[]{"static", "final", "const", "constant"},
            1, "Java Básico"
        ));
        baseDeDatos.add(new Pregunta(
            "¿Cuál es el resultado de la operación 5 + 3 * 2?",
            new String[]{"16", "11", "10", "13"},
            1, "Lógica y Aritmética"
        ));
    }

    private static void menuCreador() {
        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ CREADOR DE PREGUNTAS ---");
            System.out.println("1. Crear nueva pregunta");
            System.out.println("2. Listar preguntas existentes");
            System.out.println("3. Actualizar una pregunta");
            System.out.println("4. Eliminar una pregunta");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    crearPregunta();
                    break;
                case 2:
                    listarPreguntas();
                    break;
                case 3:
                    actualizarPregunta();
                    break;
                case 4:
                    eliminarPregunta();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("\nOpción inválida.");
            }
        } while (opcion != 5);
    }

    private static void crearPregunta() {
        System.out.println("\n--- CREAR NUEVA PREGUNTA ---");
        System.out.print("Ingrese la categoría: ");
        String categoria = scanner.nextLine();

        System.out.print("Ingrese el enunciado de la pregunta: ");
        String enunciado = scanner.nextLine();

        String[] opciones = new String[4];
        char letra = 'A';
        for (int i = 0; i < 4; i++) {
            System.out.print("Ingrese la opción " + letra + ": ");
            opciones[i] = scanner.nextLine();
            letra++;
        }

        int correcta = -1;
        while (correcta < 0 || correcta > 3) {
            System.out.print("Ingrese la respuesta correcta (0 para A, 1 para B, 2 para C, 3 para D): ");
            if (scanner.hasNextInt()) {
                correcta = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                correcta = -1;
            }
            if (correcta < 0 || correcta > 3) {
                System.out.println("Valor inválido. Debe ser un número entre 0 y 3.");
            }
        }

        baseDeDatos.add(new Pregunta(enunciado, opciones, correcta, categoria));
        System.out.println("\n¡Pregunta creada y guardada con éxito!");
    }

    private static void listarPreguntas() {
        System.out.println("\n--- LISTADO DE PREGUNTAS ---");
        if (baseDeDatos.isEmpty()) {
            System.out.println("No hay preguntas registradas.");
            return;
        }
        for (int i = 0; i < baseDeDatos.size(); i++) {
            System.out.println("\n[ID de Pregunta: " + i + "]");
            baseDeDatos.get(i).mostrarPregunta();
        }
    }

    private static void actualizarPregunta() {
        listarPreguntas();
        if (baseDeDatos.isEmpty()) return;

        System.out.print("\nIngrese el ID de la pregunta que desea actualizar: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("ID inválido.");
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id < 0 || id >= baseDeDatos.size()) {
            System.out.println("ID fuera de rango.");
            return;
        }

        Pregunta p = baseDeDatos.get(id);
        System.out.println("\nModificando pregunta (Presione Enter sin escribir nada para conservar el valor actual):");

        System.out.print("Nueva categoría [" + p.getCategoria() + "]: ");
        String categoria = scanner.nextLine();
        if (!categoria.isEmpty()) p.setCategoria(categoria);

        System.out.print("Nuevo enunciado [" + p.getEnunciado() + "]: ");
        String enunciado = scanner.nextLine();
        if (!enunciado.isEmpty()) p.setEnunciado(enunciado);

        String[] opciones = new String[4];
        char letra = 'A';
        for (int i = 0; i < 4; i++) {
            System.out.print("Nueva opción " + letra + " [" + p.getOpciones()[i] + "]: ");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                opciones[i] = p.getOpciones()[i];
            } else {
                opciones[i] = opt;
            }
            letra++;
        }
        p.setOpciones(opciones);

        System.out.print("Nueva respuesta correcta (0=A, 1=B, 2=C, 3=D) [Actual: " + p.getRespuestaCorrecta() + "]: ");
        String corrStr = scanner.nextLine();
        if (!corrStr.isEmpty()) {
            try {
                int corr = Integer.parseInt(corrStr);
                if (corr >= 0 && corr <= 3) {
                    p.setRespuestaCorrecta(corr);
                }
            } catch (NumberFormatException e) {
                // Mantiene el valor actual si hay error
            }
        }

        System.out.println("\n¡Pregunta actualizada con éxito!");
    }

    private static void eliminarPregunta() {
        listarPreguntas();
        if (baseDeDatos.isEmpty()) return;

        System.out.print("\nIngrese el ID de la pregunta que desea eliminar: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("ID inválido.");
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id < 0 || id >= baseDeDatos.size()) {
            System.out.println("ID fuera de rango.");
            return;
        }

        baseDeDatos.remove(id);
        System.out.println("\n¡Pregunta eliminada con éxito!");
    }

    private static void menuEstudiante() {
        if (baseDeDatos.isEmpty()) {
            System.out.println("\nNo hay preguntas en la base de datos. Pide al creador que agregue algunas primero.");
            return;
        }

        System.out.println("\n--- MODO ESTUDIANTE: CUESTIONARIO ---");
        System.out.println("Preguntas totales en la base de datos: " + baseDeDatos.size());
        System.out.print("¿Cuántas preguntas deseas responder en este repaso?: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Cantidad inválida.");
            return;
        }
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
            return;
        }

        if (cantidad > baseDeDatos.size()) {
            System.out.println("No hay tantas preguntas. Se ajustará al total disponible: " + baseDeDatos.size());
            cantidad = baseDeDatos.size();
        }

        // Duplicar y mezclar para seleccionar preguntas al azar
        ArrayList<Pregunta> preguntasAleatorias = new ArrayList<>(baseDeDatos);
        Collections.shuffle(preguntasAleatorias);

        int aciertos = 0;

        for (int i = 0; i < cantidad; i++) {
            Pregunta p = preguntasAleatorias.get(i);
            System.out.println("\n----------------------------------------");
            System.out.println("Pregunta " + (i + 1) + " de " + cantidad);
            p.mostrarPregunta();

            int respuestaUsuario = -1;
            while (respuestaUsuario < 0 || respuestaUsuario > 3) {
                System.out.print("Tu respuesta (0=A, 1=B, 2=C, 3=D): ");
                if (scanner.hasNextInt()) {
                    respuestaUsuario = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    respuestaUsuario = -1;
                }
                if (respuestaUsuario < 0 || respuestaUsuario > 3) {
                    System.out.println("Opción no válida. Ingresa un número entre 0 y 3.");
                }
            }

            if (respuestaUsuario == p.getRespuestaCorrecta()) {
                System.out.println("¡Correcto!");
                aciertos++;
            } else {
                char letraCorrecta = (char) ('A' + p.getRespuestaCorrecta());
                System.out.println("Incorrecto. La respuesta correcta era la " + letraCorrecta);
            }
        }

        // Resumen de resultados al finalizar la sesión
        System.out.println("\n========================================");
        System.out.println("           RESULTADO FINAL              ");
        System.out.println("========================================");
        System.out.println("Preguntas respondidas: " + cantidad);
        System.out.println("Aciertos: " + aciertos);
        System.out.println("Errores: " + (cantidad - aciertos));
        double porcentaje = ((double) aciertos / cantidad) * 100;
        System.out.printf("Calificación final: %.2f%%\n", porcentaje);
        System.out.println("========================================");
    }
}