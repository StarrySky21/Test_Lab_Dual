import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Conversion {
    /*три списка для имени компании, времени отправления и прибытия*/
    List<Integer> depList = new ArrayList<>();
    List<Integer> arList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();

    Conversion() {
    }

    public void textFile(String input) throws IOException {
        /*работа с файлом: чтение и запись в новый была вынесена в отдельный класс, здесь создается
        * объект это класса и вызов метода чтения*/
        TextFile file = new TextFile();
        String[] a = file.readFile(input).toArray(new String[file.readFile(input).size()]);
        /*для дальнейшей работы я приняла решение разделить строки на 3 массива
        * ниже в цикле выполняется первое условие нахождение эффективного времени:
        * разница больше часа не учитывается.*/
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            String[] words = a[i].split(" ");  /*название компании*/
            String[] departureTimes = words[1].split(":");  /*время отправления*/
            String[] arrivalTime = words[2].split(":"); /*время прибытия*/
            if (compareHours(convertDepartureTime(departureTimes), convertArrivalTime(arrivalTime)) == 0) {
            } else {
                newList.add(a[i]);
                nameList.add(words[0]);
            }
        }
        /*выполнение следующих условий эффективности с созданием нового списка*/
        findEffectiveTime(depList, arList, nameList);
        List<Integer> indexList = new ArrayList<>(findEffectiveTime(depList, arList, nameList));
        List<String> lastList = selectEffictiveTime(indexList, newList); /**/

        List<String> poshList = new ArrayList<>();
        List<String> grottyList = new ArrayList<>();
        for (int i = 0; i < lastList.size(); i++) {
            String[] b = lastList.toArray(new String[lastList.size()]);
            String[] word = b[i].split(" ");
            if (word[0].equals("Posh")) {
                poshList.add(b[i]);
            } else if (word[0].equals("Grotty")) {
                grottyList.add(b[i]);
            }
        }
        file.writeFile(poshList, grottyList);

    }

    /*2 ниже приведенных метода выполняют функцию преобразования времени в минуты*/
    public int convertDepartureTime(String[] a1) //переводим время отправки в минуты
    {
        int i1 = Integer.parseInt(a1[0]);
        int i2 = Integer.parseInt(a1[1]);
        int i = (i1 * 60) + i2;
        return i;
    }

    public int convertArrivalTime(String[] a1) // переводим время прибытия в минуты
    {
        int i1 = Integer.parseInt(a1[0]);
        int i2 = Integer.parseInt(a1[1]);
        int i = (i1 * 60) + i2;
        return i;
    }

    /*метод вычисляет есть ли разница между временем отправки и прибытия больше часа
    * в качестве аргумента принимает 2 числовых значения ( время отправлени и прибытия, переведенное в минуты)*/
    public int compareHours(int a1, int a2)
    {
        if ((a1 - a2) >= 60 || (a2 - a1) >= 60) {
            return 0;
        } else {
            depList.add(a1);
            arList.add(a2);
            return 1;
        }
    }

    /*метод позволяет проверить оставшиеся условия эффективности. возвращает список с индексами,что не удовлетвояют условиям*/
    public List<Integer> findEffectiveTime(List<Integer> d, List<Integer> a, List<String> name) {
        List<Integer> indexs = new ArrayList<>();
        List<Integer> d1 = new ArrayList<>(d);
        List<Integer> a1 = new ArrayList<>(a);
        for (int i = 0; i < d.size() - 1; i++) {
            if (d.get(i).equals(d1.get(i + 1))) {
                if (a.get(i) > a1.get(i + 1)) {
                    indexs.add(i);
                }
                if (a.get(i) < a1.get(i + 1)) {
                    indexs.add(i + 1);
                }
                if (a.get(i).equals(a1.get(i + 1))) {
                    if (name.get(i).equals("Posh")) {
                        indexs.add(i + 1);
                    }
                }
            } else {
                continue;
            }
        }
        for (int i = 0; i < a.size() - 1; i++) {
            if (a.get(i).equals(a1.get(i + 1))) {
                if (d.get(i) > d1.get(i + 1)) {
                    indexs.add(i + 1);
                }
                if (d.get(i) < d1.get(i + 1)) {
                    indexs.add(i);
                }
                if (d.get(i).equals(d1.get(i + 1))) {
                    if (name.get(i).equals("Posh")) {
                        indexs.add(i + 1);
                    }
                } else {
                    continue;
                }
            }
        }
        return indexs;
    }

    /*метод, позволяющий сформировать конечный список с эфективным временем. принимает в качестве аргумента 2 списка:
     с значениями после выполнения условия "меньше часа" и с индексами,что не удовлетворяют оставшимся условиям эфективности*/
    public List<String> selectEffictiveTime(List<Integer> tempList, List<String> newList){
        /*список с индексами нужно отсортировать и убрать повторяющиеся элементы. конвертируем в set и обратно в list*/
        Set<Integer> index = new HashSet<>();
        index.addAll(tempList);
        List<Integer> indexList = new ArrayList<>();
        indexList.addAll(index);
        List<String> lastList = new ArrayList<>();
        /*переменная k будет запоминать индекс, который будет удовлетворять противоположному условия if*/
        int k = 0;
        for (int i = 0; i < indexList.size(); i++) {
            for (int i1 = k; i1 < newList.size(); i1++) {
                if (i1 != indexList.get(i)) {
                    lastList.add(newList.get(i1));
                } else {
                    k = i1 + 1;
                    break;
                }
            }
        }
        return lastList;
    }


}
