import java.util.ArrayList;

import javax.swing.JOptionPane;

import arvoreGenerica.GenericNode;

class Main {
  public static void main(String[] args) {
    teste a = new teste("aasd");
    a.setName("kkkk");

    String[] array = new String[10];
    System.out.println(array.length);
    System.out.println(array[0]);

    ArrayList<Integer> lista = new ArrayList<>();
    lista.add(1);
    lista.add(2);
    lista.add(3);

    // for (int i = 0; i < lista.size(); i++)  {
    //   if(i > 1){
    //     lista.remove(i);
    //   }
    // }
    ArrayList<teste> listaTeste = new ArrayList<>();
    listaTeste.add(a);
    listaTeste.remove(0);

    
    // lista.set(0, null);

    // System.out.println(lista.size());

    // System.out.println(a.getName());
    
    // System.out.println(listaTeste);
    System.out.println("\nArvore Genérica\n===");
      String A = "";
    System.out.print("Filhos:");
    for (int i = 0; i < lista.size(); i++) {
      A = A + "  ("+i+") "+lista.get(i) + ";";
      // System.out.print("("+i+") "+lista.get(i) + ";  ");
    }
    System.out.println(A);

    String testeInput = JOptionPane.showInputDialog("insira um valor");
    int testeInputInt = Integer.parseInt(testeInput);
    System.out.println(testeInputInt);
  }
}