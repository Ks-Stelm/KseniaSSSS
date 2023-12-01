package com.example.demo4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

    // Создаем коллекцию строк
    private static ArrayList<String> list = new ArrayList<>();

    // Создаем сканер для ввода с клавиатуры
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Выберите, что вы хотите сделать из списка:");
        System.out.println("1. Добавление и удаление объектов.");
        System.out.println("2. Поиск одинаковых элементов с подсчетом совпадений.");
        System.out.println("3. Выгрузка в xml-файл.");
        System.out.println("4. Реверс всех строк, входящих в коллекцию.");
        System.out.println("5. Статистика по всем символам, содержащимся в строках коллекции.");
        System.out.println("6. Поиск подстроки в строках коллекции.");
        System.out.println("7. Инициализация листа по текстовому файлу и вывод содержимого коллекции на экран.");
        System.out.println("8. Расширить функциональность класса ArrayList .");
        System.out.println("9. Посчитать длины срок входящих в коллекцию, и вывести результат в упорядоченном виде.");
        System.out.println("10. Реализовать возможность добавления в динамическую коллекцию, как если бы она была статической размерности, т.е. задаем статическую размерность коллекции, пока количество объектов меньше заданной размерности, происходит только добавление объектов, при достижении порогового значения, добавление нового элемента вызывает удаление первого элемента коллекции.");
        System.out.println("0. Выход из программы.");

        // Читаем выбор пользователя
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addAndRemove();
                break;
            case 2:
                findDuplicates();
                break;
            case 3:
                exportToXml();
                break;
            case 4:
                reverseStrings();
                break;
            case 5:
                countCharacters();
                break;
            case 6:
                findSubstring();
                break;
            case 7:
                initFromFile();
                break;
            case 8:
                compareInnerObjects();
                break;
            case 9:
                sortLengths();
                break;
            case 10:
                addWithLimit();
                break;
            case 0:
                System.out.println("Спасибо за использование программы. До свидания!");
                System.exit(0);
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте еще раз.");
                main(args); // Повторяем меню
        }
    }

    private static void addAndRemove() {
        System.out.println("Вы выбрали опцию 1. Добавление и удаление объектов.");
        System.out.println("Введите 1, если хотите добавить элемент в коллекцию, или 2, если хотите удалить элемент из коллекции, или 0 для выхода в главное меню.");
        int choice = scanner.nextInt();
        if (choice == 0) {
            main(null);
        } else if (choice == 1) {
            System.out.println("Введите элемент, которую хотите добавить в коллекцию, или 0 для выхода в главное меню.");
            String input = scanner.next();
            if (input.equals("0")) {
                main(null);
            } else {
                list.add(input); // Добавляем строку в коллекцию
                System.out.println("Элемент " + input + " добавлен в коллекцию.");
                addAndRemove(); // Повторяем
            }
        } else if (choice == 2) {
            System.out.println("Введите индекс элемента, который хотите удалить из коллекции, или -1 для выхода в главное меню.");
            int index = scanner.nextInt();
            if (index == -1) {
                main(null);
            } else {
                try {
                    String removed = list.remove(index); // Удаляем элемент по индексу
                    System.out.println("Элемент " + removed + " удален из коллекции.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Неверный индекс. Попробуйте еще раз.");
                }
                addAndRemove();
            }
        } else {
            System.out.println("Неверный выбор. Попробуйте еще раз.");
            addAndRemove();
        }
    }


    private static void findDuplicates() {
        System.out.println("Вы выбрали опцию 2. Поиск одинаковых элементов с подсчетом совпадений.");
        // Создаем карту, где ключ - элемент коллекции, а значение - количество его повторений
        Map<String, Integer> map = new HashMap<>();
        for (String s : list) {
            // Если элемент уже есть в карте, увеличиваем его значение на 1
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                // Иначе добавляем элемент в карту с значением 1
                map.put(s, 1);
            }
        }
        System.out.println("В коллекции есть следующие повторяющиеся элементы:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            // Выводим только те элементы, у которых значение больше 1
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " - " + entry.getValue() + " раза");
            }
        }
        System.out.println("Введите 0 для выхода в главное меню.");
        int input = scanner.nextInt();
        if (input == 0) {
            main(null);
        } else {
            System.out.println("Неверный ввод. Попробуйте еще раз.");
            findDuplicates();
        }
    }

    private static void exportToXml() {
        System.out.println("Вы выбрали опцию 3. Выгрузка в xml-файл.");
        System.out.println("Введите имя файла, в который хотите сохранить коллекцию, или 0 для выхода в главное меню.");
        String fileName = scanner.next();
        if (fileName.equals("0")) {
            main(null);
        } else {
            try {
                // Создаем документ для xml
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                // статический метод, который создает новый экземпляр фабрики документов. Фабрика документов используется для создания документов xml.
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                // новый экземпляр фабрики
                Document doc = dBuilder.newDocument();
                // новый пустой документ

                // Создаем корневой элемент
                Element rootElement = doc.createElement("collection");
                // Добавляем корневой элемент в документ
                doc.appendChild(rootElement);
                // Добавляем корневой элемент в документ. который добавляет узел в качестве последнего дочернего узла к заданному родительскому узлу

                // Добавляем элементы коллекции в документ
                for (String s : list) {
                    // Создаем элемент для каждой строки
                    Element element = doc.createElement("element");
                    // Добавляем текстовое содержимое элемента
                    element.appendChild(doc.createTextNode(s));
                    // Добавляем элемент в корневой элемент
                    rootElement.appendChild(element);
                }

                // Сохраняем документ в файл
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                // это статический метод, который создает новый экземпляр фабрики трансформеров. Фабрика трансформеров используется для создания трансформеров, которые могут преобразовывать документы xml в разные форматы.
                Transformer transformer = transformerFactory.newTransformer();
                // Экспортирует в хмл. Этот метод создает новый объект трансформера, который выполняет копирование источника в результат.
                // Устанавливаем свойство для форматирования xml, отступы
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                // Создаем источник для трансформации из узла документа(входн данн)
                DOMSource source = new DOMSource(doc);
                // Создаем результат для трансформации в файл
                StreamResult result = new StreamResult(new File(fileName));
                // Выполняем трансформацию
                transformer.transform(source, result);

                System.out.println("Коллекция успешно сохранена в файл " + fileName);
            } catch (ParserConfigurationException | TransformerException e) {
                System.out.println("Произошла ошибка при сохранении в файл. Попробуйте еще раз.");
            }
            System.out.println("Введите 0 для выхода в главное меню.");
            int input = scanner.nextInt();
            if (input == 0) {
                main(null);
            } else {
                System.out.println("Неверный ввод. Попробуйте еще раз.");
                exportToXml();
            }
        }
    }
            private static void reverseStrings() {
                System.out.println("Вы выбрали опцию 4. Реверс всех строк, входящих в коллекцию.");
                // Создаем новую коллекцию для хранения реверсированных стр
                ArrayList<String> reversed = new ArrayList<>();
                // Проходим по всем элементам исходной коллекции
                for (String s : list) {
                    // Строка-буфер для хранения реверсированной строки
                    StringBuilder sb = new StringBuilder();
                    // Проходим в обратном порядке
                    for (int i = s.length() - 1; i >= 0; i--) {
                        // Добавляем символ в буфер
                        sb.append(s.charAt(i));
                    }
                    // Добавляем реверсированную строку в новую коллекцию
                    reversed.add(sb.toString());
                }
                System.out.println("Коллекция после реверса всех строк:");
                for (String s : reversed) {
                    System.out.println(s);
                }
                System.out.println("Введите 0 для выхода в главное меню.");
                int input = scanner.nextInt();
                if (input == 0) {
                    main(null);
                } else {
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
                    reverseStrings();
                }
            }


            private static void countCharacters() {
                System.out.println("Вы выбрали опцию 5. Статистика по всем символам, содержащимся в строках коллекции.");
                // Создаем карту, где ключ - символ, а значение - количество его вхождений в коллекцию
                Map<Character, Integer> map = new HashMap<>();
                // элементам коллекции
                for (String s : list) {
                    // символам элемента
                    for (char c : s.toCharArray()) {
                        // Если символ уже есть в карте, увеличиваем его значение на 1
                        if (map.containsKey(c)) {
                            map.put(c, map.get(c) + 1);
                        } else {
                            // Иначе добавляем символ в карту с значением 1
                            map.put(c, 1);
                        }
                    }
                }
                System.out.println("В коллекции есть следующие символы и их количество:");
                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + " - " + entry.getValue() + " раза");
                }
                System.out.println("Введите 0 для выхода в главное меню.");
                int input = scanner.nextInt();
                if (input == 0) {
                    main(null);
                } else {
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
                    countCharacters();
                }
            }
                private static void findSubstring() {
                    System.out.println("Вы выбрали опцию 6. Поиск подстроки в строках коллекции.");
                    System.out.println("Введите подстроку, которую хотите найти, или 0 для выхода в главное меню.");
                    String substring = scanner.next();
                    if (substring.equals("0")) {
                        main(null);
                    } else {
                        // Создаем новую коллекцию для хранения индексов строк, в которых есть подстрока
                        ArrayList<Integer> indexes = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            // Если элемент содержит подстроку, добавляем его индекс в новую коллекцию
                            if (list.get(i).contains(substring)) {
                                indexes.add(i);
                            }
                        }
                        System.out.println("Подстрока " + substring + " найдена в следующих строках коллекции:");
                        for (int i : indexes) {
                            System.out.println(list.get(i));
                        }
                        System.out.println("Введите 0 для выхода в главное меню.");
                        int input = scanner.nextInt();
                        if (input == 0) {
                            main(null);
                        } else {
                            System.out.println("Неверный ввод. Попробуйте еще раз.");
                            findSubstring();
                        }
                    }
                }

    private static void initFromFile() {
        System.out.println("Вы выбрали опцию 7. Инициализация листа по текстовому файлу и вывод содержимого коллекции на экран.");
        System.out.println("Введите имя файла, из которого хотите загрузить коллекцию, или 0 для выхода в главное меню.");
        String fileName = scanner.next();
        if (fileName.equals("0")) {
            main(null);
        } else {
            try {
                // Создаем сканер для чтения из файла
                Scanner fileScanner = new Scanner(new File(fileName));

                // Читаем все строки из файла и добавляем их в коллекцию
                while (fileScanner.hasNextLine()) {
                    list.add(fileScanner.nextLine());
                }
                fileScanner.close();
                System.out.println("Коллекция успешно загружена из файла " + fileName);
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден. Попробуйте еще раз.");
            }
            System.out.println("Коллекция содержит следующие элементы:");
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("Введите еще одно имя файла, из которого хотите загрузить коллекцию, или 0 для выхода в главное меню.");
            initFromFile();
        }
    }

    // Метод для сравнения внутренних объектов коллекции по индексам
        private static void compareInnerObjects() {
        System.out.println("Вы выбрали опцию 8. Расширить функциональность класса ArrayList методом compareInnerObjects ( int firstIndex, int secondIndex ).");
        System.out.println("Введите первый индекс строки коллекции, который хотите сравнить, или -1 для выхода в главное меню.");
        int firstIndex = scanner.nextInt();
        if (firstIndex == -1) {
            main(null); // Возвращаемся в главное меню
        } else {
            System.out.println("Введите второй индекс строки коллекции, который хотите сравнить, или -1 для выхода в главное меню.");
            int secondIndex = scanner.nextInt();
            if (secondIndex == -1) {
                main(null);
            } else {
                try {
                    // Получаем строки по индексам
                    String first = list.get(firstIndex);
                    String second = list.get(secondIndex);
                    // Сравниваем их с помощью метода compareTo
                    int result = first.compareTo(second);
                    // Выводим результат
                    if (result < 0) {
                        System.out.println("Строка " + first + " больше строки " + second);
                    } else if (result > 0) {
                        System.out.println("Строка " + first + " меньше строки " + second);
                    } else {
                        System.out.println("Строка " + first + " и " + second + " равны");
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Выводим сообщение об ошибке
                    System.out.println("Неверный индекс. Попробуйте еще раз.");
                }
                compareInnerObjects(); // Повторяем опцию
            }
        }
    }




    // Метод для сортировки длин строк, входящих в коллекцию
                    private static void sortLengths() {
                        System.out.println("Вы выбрали опцию 9. Посчитать длины срок входящих в коллекцию, и вывести результат в упорядоченном виде.");
                        // Создаем новую коллекцию для хранения длин строк
                        ArrayList<Integer> lengths = new ArrayList<>();
                        // Проходим по всем элементам исходной коллекции
                        for (String s : list) {
                            // Добавляем длину строки в новую коллекцию
                            lengths.add(s.length());
                        }
                        // Сортируем новую коллекцию по возрастанию
                        Collections.sort(lengths);
                        // Выводим результат
                        System.out.println("Длины строк в коллекции в упорядоченном виде:");
                        for (int length : lengths) {
                            System.out.println(length);
                        }
                        System.out.println("Введите 0 для выхода в главное меню.");
                        int input = scanner.nextInt();
                        if (input == 0) {
                            main(null); // Возвращаемся в главное меню
                        } else {
                            System.out.println("Неверный ввод. Попробуйте еще раз.");
                            sortLengths(); // Повторяем опцию
                        }
                    }

                    private static void addWithLimit() {
                        System.out.println("Вы выбрали опцию 10. Реализовать возможность добавления в динамическую коллекцию, как если бы она была статической размерности.");
                        System.out.println("Введите статическую размерность коллекции, или 0 для выхода в главное меню.");
                        int limit = scanner.nextInt();
                        if (limit == 0) {
                            main(null);
                        } else {
                            System.out.println("Введите строку, которую хотите добавить в коллекцию, или 0 для выхода в главное меню.");
                            String input = scanner.next();
                            if (input.equals("0")) {
                                main(null);
                            } else {
                                // Проверяем, не достигнута ли статическая размерность
                                if (list.size() == limit) {
                                    // Если да, то удаляем первый элемент коллекции
                                    String removed = list.remove(0);
                                    System.out.println("Элемент " + removed + " удален из коллекции, так как достигнута статическая размерность.");
                                }
                                // Добавляем новый элемент в конец коллекции
                                list.add(input);
                                System.out.println("Элемент " + input + " добавлен в коллекцию.");
                                addWithLimit(); // Повторяем опцию
                            }
                        }
                    }

                }






