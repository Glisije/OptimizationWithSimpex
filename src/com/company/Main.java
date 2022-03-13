package com.company;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

//  eps = 0.1, n = 2, m = 0.5; base = (1,1)

    //Точность искомого значения функции
    static double eps;
    //Размерность
    static int n;
    //Длина грани
    static double m;
    //Стартовый вектор
    static MyVector base;

    //Функция для печати таблицы
    public static void printTable(ArrayList<MyVector> vecAr){
        NumberFormat x = NumberFormat.getInstance();
        x.setMaximumFractionDigits(3);
        x.setMinimumFractionDigits(3);
        NumberFormat fx = NumberFormat.getInstance();
        fx.setMaximumFractionDigits(4);
        fx.setMinimumFractionDigits(4);
        System.out.print("\t\t\t\t-----------------------------------------------------");
        System.out.print("\n\t\t\t\t|\tКоординаты вершины\t\t|\tЗначение функции\t|");
        System.out.print("\n\t\t\t\t----------------------------");
        System.out.print("\n\t\t\t\t|\t\t1     |      2      |\t\t\t\t\t\t|");
        System.out.print("\n---------------------------------------------------------------------");
        for (int i = 0; i < vecAr.size(); i++) {
            System.out.print("\n|Номер вершины "+i+"| x1("+i+")="+x.format(vecAr.get(i).x1)+" | x2("+i+")="+x.format(vecAr.get(i).x2)+" | \tf(x("+i+")) = "+fx.format(vecAr.get(i).fx)+"\t|");
            System.out.print("\n---------------------------------------------------------------------");
        }

    }

    //Функция для вычисления центра тяжести симплекса
    public static MyVector getTyazh(ArrayList<MyVector> workCoords){
        MyVector xc = new MyVector(0,0);
        for (MyVector workCoord : workCoords) {
            xc.x1 += workCoord.x1;
            xc.x2 += workCoord.x2;
        }
        xc.x1/=workCoords.size();
        xc.x2/=workCoords.size();
        xc.reWrightFX();
        return xc;
    }

    //Функция для поиска вершины с максимальным значнеием f(x) в рабочем списке вершин
    public static MyVector findMax(ArrayList<MyVector> mainAr){
        MyVector maxVec = new MyVector(0,0, 1);
        for (MyVector myVector : mainAr) {
            if (myVector.fx > maxVec.fx) maxVec = myVector;
        }
        return maxVec;
    }

    //Функция реализующая проверку условия остановки поиска
    public static boolean trueFalse(ArrayList<MyVector> vecAr, MyVector fxc){
        MyVector workVector = new MyVector(0,0);
        for (MyVector myVector : vecAr) {
            workVector.x1 = myVector.x1 - fxc.x1;
            workVector.x2 = myVector.x2 - fxc.x2;
            workVector.reWrightFX();
            if (workVector.fx - fxc.fx > eps) return false;
        }
        return true;
    }

    //Функция реализующая вычисление новой координаты отраженной вершины благодаря свойству регулярности
    public static MyVector newVersh(ArrayList<MyVector> workCoords, MyVector otrazh){
        return new MyVector((2* getTyazh(workCoords).x1)- otrazh.x1, (2*getTyazh(workCoords).x2)-otrazh.x2);
    }

    public static void main(String[] args) {
        // Определяем начальные значения
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите точность (eps): ");
        eps = sc.nextDouble();
        System.out.print("\nВведите размерность задачи (n): ");
        n = sc.nextInt();
        System.out.print("\nВведите длину ребра симплекса (m): ");
        m = sc.nextDouble();
        System.out.print("\nВведите координату x1 для начальной точки: ");
        double x1_time = sc.nextDouble();
        System.out.print("Введите координату x2 для начальной точки: ");
        double x2_time = sc.nextDouble();
        base = new MyVector(x1_time, x2_time);

        //Вычисляем приращения и две вершины симплекса
        ArrayList<MyVector> allCoords = new ArrayList<>();
        allCoords.add(base);
        printTable(allCoords);
        double[] del = new double[2];
        del[0] = ((Math.sqrt(n+1)-1)/(n*Math.sqrt(2))) * m;
        del[1] = ((Math.sqrt(n+1)+n-1)/(n*Math.sqrt(2))) * m;
        allCoords.add(new MyVector(del[0]+ base.x1, del[1]+ base.x2));
        allCoords.add(new MyVector(del[1]+ base.x1, del[0]+ base.x2));

        //Инициализируем список с рабочими вершинами
        ArrayList<MyVector> workCoords = new ArrayList<>(allCoords);
        int i = 0;

        //Проводим нулевую итеарцию
        System.out.println("\n\n");
        printTable(allCoords);
        MyVector max = findMax(workCoords);
        workCoords.remove(max);
        allCoords.add(newVersh(workCoords, max));
        workCoords.add(newVersh(workCoords, max));
        MyVector fxc = getTyazh(workCoords);

        //Проверка условий окончания поиска
        while (trueFalse(workCoords, fxc)){
            //Выполнение каждой итерации
            System.out.println("\n\nИтерация: "+ (i));
            printTable(allCoords);
            i++;
            max = findMax(workCoords);
            workCoords.remove(max);
            allCoords.add(newVersh(workCoords, max));
            workCoords.add(newVersh(workCoords, max));
            fxc = getTyazh(workCoords);
        }

        System.out.println("\nИтерация: "+ i);
        printTable(allCoords);

    }

}
