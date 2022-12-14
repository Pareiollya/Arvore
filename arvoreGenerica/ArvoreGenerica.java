package arvoreGenerica;

import java.util.ArrayList;

public class ArvoreGenerica<T> {
    private GenericNode<T> raiz;
    private int type; // 0 para generica, 1 para binaria

    public ArvoreGenerica() {
        this.type = 0; // 0 e 1 = generica / binaria, 2 = binaria, 3 = binaria de busca
        this.raiz = null;
    }

    public ArvoreGenerica(T valor) { // instanciar com uma raiz de valor X
        this.type = 0;
        this.raiz = new GenericNode<T>(valor);
    }

    public GenericNode<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(GenericNode<T> raiz) {
        this.raiz = raiz;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public boolean isRoot(GenericNode<T> no) {
        if (no == this.raiz || no.pai == null) {
            return true;
        } else {
            return false;
        }
    }

    GenericNode<T> noResultado = null;

    public GenericNode<T> buscar(T valor, GenericNode<T> no) { // retornar um nó pelo seu valor
        no = setNodeToRoot(no);
        if (this.raiz.getValor() == valor) {
            noResultado = this.raiz;
        }

        if (this.type < 2) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().get(i) != null) {
                        if (no.getFilhos().get(i).getValor() != valor) { // || no.getFilhos().get(i).getValor() == null
                            buscar(valor, no.getFilhos().get(i));
                        } else {
                            noResultado = no.getFilhos().get(i);
                        }
                    }
                }
            }
        } else {
            if (no.getValor() == valor) {
                noResultado = no;
            }
            if (no.hasLeft()) {
                if (no.getLeft().getValor() == valor)
                    noResultado = no.getLeft();
                else
                    buscar(valor, no.getLeft());

            }
            if (no.hasRight()) {
                if (no.getRight().getValor() == valor)
                    noResultado = no.getRight();
                else
                    buscar(valor, no.getRight());
            }
        }

        try {
            if (noResultado.getValor() != valor) {
                noResultado = null;
            }
        } catch (Exception e) {
            throw new NullPointerException("Nó inválido (null).");
        } finally {
            // System.out.println(noResultado.getValor());
            return noResultado;
        }

    }

    public void inserirNode(T no, T novoValor) { // no que quer adicionar o filho / valor que quer adicionar
        GenericNode<T> noPai = buscar(no, this.raiz);
        noPai = setNodeToRoot(noPai);
        if (type == 0 && noPai != null) {
            GenericNode<T> noFilho = new GenericNode<T>(novoValor, noPai);
            noPai.addFilho(noFilho);

            System.out.println("Novo filho " + novoValor + " adicionado ao nó " + no);

        } else if (type == 1 && noPai.getFilhos().size() == 2 && noPai != null) { // caso seja binaria não inserir mais
                                                                                  // de 2
            System.out.println(hasLeft(noPai));
            if (hasLeft(noPai) == false) {
                GenericNode<T> noFilho = new GenericNode<T>(novoValor, noPai);
                noPai.getFilhos().add(0, noFilho);
                System.out.println("> Novo filho " + novoValor + " adicionado a esquerda do nó " + no);
            }
            if (hasRight(noPai) == false) {
                GenericNode<T> noFilho = new GenericNode<T>(novoValor, noPai);
                noPai.getFilhos().add(1, noFilho);
                System.out.println("> Novo filho " + novoValor + " adicionado a direita do nó " + no);
            }
            // noPai.addFilho(noFilho);
            // verificar e dar prioridade pra

        } else {
            System.out.println("> Não é possível inserir um novo filho em " + no);
        }
    }

    // public void showTreeWithNode(T valor){
    //     GenericNode<T> no = buscar(valor, null);
    //     no = setNodeToRoot(no);
    //     System.out.println("show arvore\n" + no.getValor());
    //     showTree(no);
    // }

    public void showTree(GenericNode<T> no) { // caso passar um no exibir a sub arvore (igual a busca)
        no = setNodeToRoot(no);
        if (this.type == 0 || this.type == 1) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().size() > 0 && no.getFilhos().get(i) != null) { // ||
                                                                                      // no.getFilhos().get(i).getValor()
                                                                                      // == null
                        System.out.print("  ");
                        for (int j = 0; j < no.getProfundidade(); j++) {
                            System.out.print("  ");
                        }
                        System.out.println(no.getFilhos().get(i).getValor());
                        showTree(no.getFilhos().get(i));
                    }
                }
            }
        } else {
            if(isRoot(no)){
                System.out.println("\nShow Tree\n " + no.getValor());
              }
            if (no.hasLeft()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(no.getLeft().getValor());
                showTree(no.getLeft());

            }
            if (no.hasRight()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(no.getRight().getValor());
                showTree(no.getRight());
            }
        }

    }

    public void showSubarvores(GenericNode<T> no) {
        no = setNodeToRoot(no);
        ArrayList<GenericNode<T>> internosList = getInternosList(no);
        for (int i = 0; i < internosList.size(); i++) {
            System.out.println("\nSub-arvore " + (i + 1));
            System.out.println(internosList.get(i).getValor());
            showTree(internosList.get(i));
        }
        this.clear();
        System.out.println("");
    }

    public void showAlturas(GenericNode<T> no) { // caso passar um no exibir a sub arvore (igual a busca)
        no = setNodeToRoot(no);
        if (this.type <= 1) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().size() > 0 && no.getFilhos().get(i) != null) { // ||
                                                                                      // no.getFilhos().get(i).getValor()
                                                                                      // == null
                        System.out.print("  ");
                        for (int j = 0; j < no.getProfundidade(); j++) {
                            System.out.print("  ");
                        }
                        System.out.println(getAlturaNode(no.getFilhos().get(i)));
                        showAlturas(no.getFilhos().get(i));
                    }
                }
            }
        } else {
            if(isRoot(no)){
                System.out.println("\nAlturas\n  " + getAlturaNode(no));
              }
            if (no.hasLeft()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                // por algum motivo esta retornando com 1 a mais
                System.out.println(getAlturaNode(no.getLeft()));
                showAlturas(no.getLeft());

            }
            if (no.hasRight()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(getAlturaNode(no.getRight()));
                showAlturas(no.getRight());
            }
        }
    }

    public void showProfundidades(GenericNode<T> no) { // caso passar um no exibir a sub arvore (igual a busca)
        no = setNodeToRoot(no);
        if (this.type <= 1) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().size() > 0 && no.getFilhos().get(i) != null) { // ||
                                                                                      // no.getFilhos().get(i).getValor()
                                                                                      // == null
                        System.out.print("  ");
                        for (int j = 0; j < no.getProfundidade(); j++) {
                            System.out.print("  ");
                        }
                        System.out.println(getProfundidadeFromNode(this.raiz, no) + 1);
                        showProfundidades(no.getFilhos().get(i));
                    }
                }
            }
        } else {
            if(isRoot(no)) {
                System.out.println("\nProfundidades\n  " + getProfundidade(no, no) + 1);
            }
            if (no.hasLeft()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(getProfundidadeFromNode(this.raiz, no) + 1);
                showProfundidades(no.getLeft());

            }
            if (no.hasRight()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(getProfundidadeFromNode(this.raiz, no) + 1);
                showProfundidades(no.getRight());
            }
        }
    }

    public void showGraus(GenericNode<T> no) { // caso passar um no exibir a sub arvore (igual a busca)
        no = setNodeToRoot(no);
        if (this.type <= 1) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().size() > 0 && no.getFilhos().get(i) != null) { // ||
                                                                                      // no.getFilhos().get(i).getValor()
                                                                                      // == null
                        System.out.print("  ");
                        for (int j = 0; j < no.getProfundidade(); j++) {
                            System.out.print("  ");
                        }
                        System.out.println(no.getFilhos().get(i).getGrau());
                        showGraus(no.getFilhos().get(i));
                    }
                }
            }
        } else {
            if(isRoot(no)) {
                System.out.println("\nGraus\n  " + no.getGrauBinary());
            }
            if (no.hasLeft()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(no.getLeft().getGrauBinary());
                showGraus(no.getLeft());

            }
            if (no.hasRight()) {
                System.out.print("  ");
                for (int j = 0; j < this.getProfundidade(null, no); j++) {
                    System.out.print("  ");
                }
                System.out.println(no.getRight().getGrauBinary());
                showGraus(no.getRight());
            }
        }
    }

    public ArrayList<GenericNode<T>> getNodesList(GenericNode<T> no) {
        ArrayList<GenericNode<T>> nodes = new ArrayList<>();
        nodes = returnNodes(no, nodes);
        return nodes;
    }
    public ArrayList<GenericNode<T>> returnNodes(GenericNode<T> no, ArrayList<GenericNode<T>> nodes) {
        no = setNodeToRoot(no);
        if (this.type <= 1) {
            int maxCount = no.getFilhos().size();
                if (no.isRoot() == false) {
                    nodes.add(no);
                }
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().get(i) != null) {
                        returnInternosList(no.getFilhos().get(i), nodes);
                    }

                }
        
        }
        if (this.type > 1) {
                // System.out.println("grau: " + no.getGrauBinary());
                if (no != this.raiz) {
                    nodes.add(no);
                }
                if (no.hasLeft()) {
                    returnInternosList(no.getLeft(), nodes);
                }
                if (no.hasRight()) {
                    returnInternosList(no.getRight(), nodes);
                }
        }
        return nodes;
    }

    public ArrayList<GenericNode<T>> getFolhas(GenericNode<T> no) {
        ArrayList<GenericNode<T>> folhas = new ArrayList<>();
        folhas = returnFolhas(no, folhas);
        return folhas;
    }

    // folhas = new ArrayList<>();
    public ArrayList<GenericNode<T>> returnFolhas(GenericNode<T> no, ArrayList<GenericNode<T>> folhas) {
        no = setNodeToRoot(no);

        if (this.type <= 1) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().get(i) != null) {
                        if (no.getFilhos().size() > 0) { // || no.getFilhos().get(i).getValor() == null
                            returnFolhas(no.getFilhos().get(i), folhas);
                        }
                    }
                    if (no.getFilhos().size() >= 2 && hasLeft(no) == false && hasRight(no) == false
                            && folhas.contains(no) == false) {
                        folhas.add(no);
                    }
                }
            } else {
                folhas.add(no);
            }

        }
        if (this.type > 1) {
            if (no.getGrauBinary() > 0) {
                if (no.hasLeft()) {
                    returnFolhas(no.getLeft(), folhas);
                }
                if (no.hasRight()) {
                    returnFolhas(no.getRight(), folhas);
                }
            } else {
                folhas.add(no);
            }
        }
        return folhas;
    }

    public ArrayList<GenericNode<T>> getInternosList(GenericNode<T> no) {
        no = setNodeToRoot(no);
        ArrayList<GenericNode<T>> internosList = new ArrayList<>();
        internosList = returnInternosList(no, internosList);
        return internosList;
    }

    public ArrayList<GenericNode<T>> returnInternosList(GenericNode<T> no, ArrayList<GenericNode<T>> internosList) {
        no = setNodeToRoot(no);
        if (this.type <= 1) {
            int maxCount = no.getFilhos().size();
            if (maxCount > 0) {
                if (no.isRoot() == false) {
                    internosList.add(no);
                }
                for (int i = 0; i < maxCount; i++) {
                    if (no.getFilhos().get(i) != null) {
                        returnInternosList(no.getFilhos().get(i), internosList);
                    }

                }
            }
        }
        if (this.type > 1) {
            if (no.getGrauBinary() > 0) {
                // System.out.println("grau: " + no.getGrauBinary());
                if (no != this.raiz) {
                    internosList.add(no);
                }
                if (no.hasLeft()) {
                    returnInternosList(no.getLeft(), internosList);
                }
                if (no.hasRight()) {
                    returnInternosList(no.getRight(), internosList);
                }
            }
        }
        return internosList;
    }

    public int getAlturaThree() { // pegar altura atravez das folhas
        int altura_arvore = 0;
        ArrayList<GenericNode<T>> folhasList = getFolhas(null);

        for (int i = 0; i < folhasList.size(); i++) {
            if (folhasList.get(i).getProfundidade() > altura_arvore) {
                altura_arvore = folhasList.get(i).getProfundidade();
            }
        }
        return altura_arvore;
    }
    public int getAlturaThree(GenericNode<T> no) { // pegar altura atravez das folhas
        int altura_arvore = 0;
        ArrayList<GenericNode<T>> folhasList = getFolhas(no);
        System.out.println(folhasList);
        for (int i = 0; i < folhasList.size(); i++) {
            if (folhasList.get(i).getProfundidade() > altura_arvore) {
                altura_arvore = folhasList.get(i).getProfundidade();
            }
        }
        return altura_arvore;
    }

    public int getProfundidade(GenericNode<T> Arvore, GenericNode<T> no) {
        if (Arvore == null) {
            Arvore = this.raiz;
        }
        if (this.raiz == no) {
            return 0;
        } else {
            return 1 + getProfundidade(Arvore, no.getPai());
        }

    }

    // caso usar a raiz, irá retornar altura da arvore
    public int getProfundidadeFromNode(GenericNode<T> noAlvo, GenericNode<T> noFilho) { // retornar profundidade de
                                                                                        // umno}
        if (noFilho.isRoot()) {
            return 0;
        }
        if (noAlvo.getValor() == noFilho.getValor()) {
            return 0;
        } else {
            return 1 + getProfundidadeFromNode(noAlvo, noFilho.getPai());
        }
    }

    public int getAlturaNode(GenericNode<T> no) { // atravez das folhas de sua sub árvore retornar a altura do nó
                                                  // especificado.
        ArrayList<GenericNode<T>> folhasList = getFolhas(no);
        int maxCount = folhasList.size();

        int aux = 0;
        int altura = 0;
        if (maxCount > 0) {
            for (int i = 0; i < maxCount; i++) {
                if (getProfundidadeFromNode(no, folhasList.get(i)) > altura) {
                    altura = getProfundidadeFromNode(no, folhasList.get(i));
                }
            }
        }
        return altura;
    }

    public int getMaxNodeGrau(GenericNode<T> no) {
        no = setNodeToRoot(no);
        int maxCount = no.getFilhos().size();
        int maxGrau = no.getGrau(); // maior grau será definido a partir do primeiro nó instanciado
        if (maxCount > 0) {
            for (int i = 0; i < maxCount; i++) { // percorrer todos os nós em busca do maior grau dentree eles
                if (no.getFilhos().get(i) != null) {
                    if (no.getFilhos().get(i).getGrau() > maxGrau) {
                        maxGrau = no.getFilhos().get(i).getGrau();
                    }
                }
            }
        }
        return maxGrau;
    }

    public void showFilhos(GenericNode<T> node) {
        if (node.getGrau() > 0 && this.type < 2) {
            System.out.print("Filhos: ");
            for (int i = 0; i < node.getFilhos().size(); i++) {
                if (node.getFilhos().get(i) != null) {
                    System.out.print(node.getFilhos().get(i).getValor() + ";  ");
                }
            }
        } else if (node.getGrauBinary() > 0 && this.type >= 2) {
            if (node.getGrauBinary() == 2) {
                System.out.print("Filho Esquerdo: " + node.getLeft().getValor() + " | " + "Filho direito: "
                        + node.getRight().getValor());
            } else if (node.hasLeft() == true && node.hasRight() == false) {
                System.out.print(
                        "Filho Esquerdo: " + node.getLeft().getValor() + " | " + "Filho direito: " + node.getRight());
            } else if (node.hasLeft() == false && node.hasRight() == true) {
                System.out.print(
                        "Filho Esquerdo: " + node.getLeft() + " | " + "Filho direito: " + node.getRight().getValor());
            }

        } else {
            System.out.print("Não possui filhos.");
        }
    }

    public void showInternos(GenericNode<T> no) { // imprimir nós internos de uma (sub)arvore
        no = setNodeToRoot(no);
        ArrayList<GenericNode<T>> internosList = getInternosList(no);
        if (internosList.contains(no)) {
            internosList.remove(0);
        }
        if (internosList.size() > 0) {
            System.out.print("\nNós Internos de " + no.getValor() + ": ");
            // System.out.print("\nNós Internos de " + no.getValor() + "(" +
            // internosList.size() + "): ");
            for (int i = 0; i < internosList.size(); i++) {
                System.out.print(internosList.get(i).getValor() + " ");
            }
            // System.out.println("");
        } else {
            System.out.println("\nEsta (sub)arvore não possui nós internos.");
        }
    }

    public void showFolhas(GenericNode<T> no) { // imprimir lista de folhas de uma (sub)arvore
        no = setNodeToRoot(no);
        ArrayList<GenericNode<T>> folhas = getFolhas(no);
        if (folhas.size() > 0) {
            System.out.print("\nNós Folhas de " + no.getValor() + ": ");
            // System.out.print("\nNós Folhas de " + no.getValor() + "(" + folhas.size() +
            // "): ");
            for (int i = 0; i < folhas.size(); i++) {
                System.out.print(folhas.get(i).getValor() + " ");
            }
            System.out.println("");
        } else {
            System.out.println("\nEsta (sub)arvore não possui nós folhas.");
        }
    }

    public void showNodeType(GenericNode<T> node) {
        if (isRoot(node)) {
            System.out.println("Tipo: Nó Raiz");
        } else if (node.getGrau() > 0 || node.getGrauBinary() > 0) {
            System.out.println("Tipo: Nó Interno");
            System.out.println("Nó pai: " + node.getPai().getValor());
        } else if (node.getGrau() == 0) {
            System.out.println("Tipo: Nó Folha");
            System.out.println("Nó pai: " + node.getPai().getValor());
        }
    }

    public void showNodeParentescos(GenericNode<T> node) {
        if (this.type <= 1) {
            System.out.println("Grau: " + node.getGrau());
        } else {
            System.out.println("Grau: " + node.getGrauBinary());
        }
        System.out.println("Profundidade: " + node.getProfundidade());
        System.out.println("Altura: " + getAlturaNode(node));
    }

    public void showNodeInfo(T valor) { // imprimir informações de um nó de valor x
        GenericNode<T> node = buscar(valor, null);
        System.out.println("------ \nINFORMAÇÕES DO NÓ:\n");
        System.out.println("Valor: " + node.getValor());
        showNodeType(node);
        showNodeParentescos(node);
        showFilhos(node);
        showInternos(node);
        showFolhas(node);
        System.out.print("\n------\n");
    }
    public void showNodeInfo(GenericNode<T> node) { // imprimir informações de um nó de valor x
        System.out.println("------ \nINFORMAÇÕES DO NÓ:\n");
        System.out.println("Valor: " + node.getValor());
        showNodeType(node);
        showNodeParentescos(node);
        showFilhos(node);
        showInternos(node);
        showFolhas(node);
        System.out.print("\n------\n");
    }

    public void removeNode(T valor) {
        GenericNode<T> node = buscar(valor, null);
        GenericNode<T> nodePai = node.getPai();

        if (this.type == 0) {
            node.setPai(null);

            nodePai.getFilhos().remove(node);

            System.out.println("Nó removido da árvore.");
        } else {
            if (nodePai.getFilhos().size() > 1) {
                removeLeft(valor);
            }
        }
    }

    public void clearTree(GenericNode<T> no) { // caso passe um no ele limpará sub arvore
        no = setNodeToRoot(no);
        no.getFilhos().clear();
        no.setLeft(null);
        no.setRight(null);
        System.out.println("(sub)árvore limpa.");

    }

    // funçoes para binaria
    public boolean hasLeft(GenericNode<T> no) {
        no = setNodeToRoot(no);
        if (no.getFilhos().size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasRight(GenericNode<T> no) {
        no = setNodeToRoot(no);
        if (no.getFilhos().size() <= 1) {
            return false;
        } else {
            return true;
        }
    }

    public GenericNode<T> getLeft(GenericNode<T> no) {
        no = setNodeToRoot(no);
        return no.getFilhos().get(0);
    }

    public GenericNode<T> getRight(GenericNode<T> no) {
        no = setNodeToRoot(no);
        return no.getFilhos().get(no.getFilhos().size() - 1);
    }

    public void addLeft(T no, T novoValor) {
        GenericNode<T> noPai = buscar(no, this.raiz);
        if (no == null) {
            noPai = this.raiz;
        }
        GenericNode<T> noFilho = new GenericNode<T>(novoValor, noPai);
        if (hasLeft(noPai) != true && this.type != 2) {
            if (noPai.getFilhos().get(0) == null) {
                noPai.getFilhos().remove(0);
            }

            noPai.getFilhos().add(0, noFilho);

        } else if (this.type > 1) {
            if (!noPai.hasLeft()) {
                noPai.setLeft(noFilho);
            }
        }

    }

    public void addRight(T no, T novoValor) {
        GenericNode<T> noPai = buscar(no, this.raiz);
        if (no == null) {
            noPai = this.raiz;
        }
        GenericNode<T> noFilho = new GenericNode<T>(novoValor, noPai);
        if (hasRight(noPai) != true && this.type <= 1) {
            if (hasLeft(noPai) != true && noPai.getFilhos().size() == 0) {
                noPai.addFilho(null);
                noPai.addFilho(noFilho);
            }
            noPai.getFilhos().remove(1);
            noPai.getFilhos().add(1, noFilho);

        } else if (this.type > 1) {

            if (!noPai.hasRight()) {
                noPai.setRight(noFilho);
                // System.out.println("adicionado direita ");
            }
        }

        // return no.getFilhos().get(no.getFilhos().size() - 1);
    }

    public void removeRight(T no) {
        GenericNode<T> noPai = buscar(no, this.raiz);
        if (no == null) {
            noPai = this.raiz;
        }
        if (hasRight(noPai) == true) {
            System.out.println("a");
            noPai.getFilhos().remove(1);
        }
    }

    public void removeLeft(T no) {
        GenericNode<T> noPai = buscar(no, this.raiz);
        if (no == null) {
            noPai = this.raiz;
        }
        if (hasLeft(noPai) == true) {
            System.out.println("a");
            noPai.getFilhos().add(0, null);
            noPai.getFilhos().remove(1);
        }
    }

    


    public void convertToBinary(GenericNode<T> no) {
        no = setNodeToRoot(no);
        this.type = 1;
        int maxCount = no.getFilhos().size();
        if (maxCount > 0) {
            for (int i = 0; i < maxCount; i++) {
                if (no.getFilhos().size() > 2 && no.getFilhos().get(i) != null) {
                    for (int j = 2; j < no.getFilhos().size(); j++) {
                        no.getFilhos().remove(j);
                        maxCount = no.getFilhos().size();
                    }
                }
                if (no.getFilhos().size() > 0 && no.getFilhos().get(i) != null) {
                    convertToBinary(no.getFilhos().get(i));
                }
            }
        }
    }

    public void convertTrueBinary(GenericNode<T> no) {
        no = setNodeToRoot(no);
        this.type = 2;
        int maxCount = no.getFilhos().size();
        if (maxCount > 0) {
            for (int i = 0; i < maxCount; i++) {
                if (no.getFilhos().size() == 1 && no.getFilhos().get(i) != null) {
                    // priorizar filho esquerdo
                    no.setLeft(no.getFilhos().get(0));
                } else {
                    // primeiro valor da lista será esquerdo, ultimo será direito
                    no.setLeft(no.getFilhos().get(0));
                    no.setRight(no.getFilhos().get(no.getFilhos().size() - 1));
                }
                if (no.getFilhos().size() > 0 && no.getFilhos().get(i) != null) {
                    convertTrueBinary(no.getFilhos().get(i));
                }
            }
        }
    }

    // FUNÇÕES PARA ARVORE BINARIA DE BUSCA
    public void convertToSearchBinary(GenericNode<T> no) {
        no = setNodeToRoot(no);
        convertTrueBinary(no);
        this.type = 3;
        reWrite(no,null);
        System.out.println("convertido para binaria de busca");
        showTree(no);
    }

    public void reWrite(GenericNode<T> no, GenericNode<T> exceptionNode){ //reescrever valores da arvore com valores antigos como arvore de busca
        no = setNodeToRoot(no);
        ArrayList<GenericNode<T>> internos = getInternosList(no);
        ArrayList<GenericNode<T>> folhas = getFolhas(no);

        if(exceptionNode != null){//exceptionNode seria um nó que nao deseja que apareça (no que deseja excluir)
            internos.remove(exceptionNode); 
            folhas.remove(exceptionNode);
            
            GenericNode<T> firstNode = getMaior(exceptionNode, null);
            // System.out.println("aslkdjaslkdj " + firstNode.getValor());
            internos.remove(firstNode); 
            //adicionar o maior nó como primeiro a ser inserido
            no.setValor(firstNode.getValor());
        }

        System.out.println(internos);
        System.out.println(folhas);
        no.setLeft(null);
        no.setRight(null);
        
        for(int i = 0; i < internos.size(); i++) {
            inserirBB(internos.get(i).getValor());
        }
        for(int i = 0; i < folhas.size(); i++) {
            inserirBB(folhas.get(i).getValor());
        }
    }

    //remover um nó binario
    //funciona para arvore binaria e bBusca

    public void removeBinary(T no){ 
        GenericNode<T> noFilho = buscar(no, this.raiz); //nó a ser removido
        GenericNode<T> noPai = buscar(no, this.raiz).getPai();
        noPai = setNodeToRoot(noPai);

        if(noFilho.getGrauBinary() == 0){ //remover um nó folha
            if(noPai.hasLeft()){
                if(noPai.getLeft().getValor() == noFilho.getValor()) noPai.setLeft(null);
            }
            if(noPai.hasRight()){
                if(noPai.getRight().getValor() == noFilho.getValor()) noPai.setRight(null);
            }
            

        }else if(noFilho.getGrauBinary() == 1){ //remover um nó interno (subarvore)
            GenericNode<T> subArvoreFilha;
            if(noFilho.hasLeft()) { 
                subArvoreFilha = noFilho.getLeft();
            }else{
                subArvoreFilha = noFilho.getRight(); 
            }

            subArvoreFilha.setPai(noPai); //definir o novo pai da subarvore.

            if(noPai.hasLeft()){ //adicionar subarvore no local removido
                if(noPai.getLeft().getValor() == noFilho.getValor()) noPai.setLeft(subArvoreFilha);
            }
            if(noPai.hasRight()){
                if(noPai.getRight().getValor() == noFilho.getValor()) noPai.setRight(subArvoreFilha);
            }
        }
        if(noFilho.getGrauBinary() == 2 && this.type < 3){ //remover um nó interno (subarvore)
            System.out.println("Não é possível remover o nó interno " + no);
        }else{
            //utilizando da reescrita
            reWrite(noFilho, noFilho);  
            //utilizando da reinserção
            //filhos do removido

            // GenericNode<T> leftFilho = noFilho.getLeft(); //6

            // GenericNode<T> rightFilho = noFilho.getRight(); //9

            // GenericNode<T> maiorEsquerdoFilho;
            // if(leftFilho.getGrauBinary() != 0)  {
            //     maiorEsquerdoFilho = getMaior(noFilho, "left"); //retornar o maior nó da subarvore esquerda //6
            // } else{
            //     maiorEsquerdoFilho = leftFilho;
            // }
            
            // maiorEsquerdoFilho.setPai(noPai); //definir novo pai do removido
            // maiorEsquerdoFilho.setLeft(null);
            // maiorEsquerdoFilho.setRight(null);
            
            
            // //filhos do maior esquedo
            // GenericNode<T> leftMaiorEsquerdoFilho = maiorEsquerdoFilho.getLeft(); //reinsrir posteriormente filhos de 6 null
            // GenericNode<T> rightMaiorEsquerdoFilho = maiorEsquerdoFilho.getRight(); //reinsrir posteriormente null


            // if(noPai.hasLeft()){ //adicionar nova subarvore no local removido
            //     if(noPai.getLeft().getValor() == noFilho.getValor()) noPai.setLeft(maiorEsquerdoFilho);
            // }
            // if(noPai.hasRight()){
            //     if(noPai.getRight().getValor() == noFilho.getValor()) noPai.setRight(maiorEsquerdoFilho);
            // }
            
            // //reinserir nós do antigo removido
            // if(maiorEsquerdoFilho != leftFilho){
            //     maiorEsquerdoFilho.setLeft(leftFilho);
            //     if( maiorEsquerdoFilho.getLeft() != null){
            //         maiorEsquerdoFilho.getLeft().setPai(maiorEsquerdoFilho);
            //     }   
                
            // }
            // maiorEsquerdoFilho.setRight(rightFilho);
            // if(maiorEsquerdoFilho.getRight() !=null){
            //     maiorEsquerdoFilho.getRight().setPai(maiorEsquerdoFilho);
            // }
            

            // noFilho.setPai(null);
            // leftFilho.setPai(maiorEsquerdoFilho);
            // rightFilho.setPai(maiorEsquerdoFilho);

            // inserirBB(noPai,leftFilho);
            // inserirBB(noPai,rightFilho);

            //inserir antigos filhos do maior
            // if(leftMaiorEsquerdoFilho != leftFilho){
            //     inserirBB(noPai, leftMaiorEsquerdoFilho);
            // }
            
            // inserirBB(noPai, rightMaiorEsquerdoFilho);

            //reinserir os antigos nós d
            //settar filhos antigos no maior 
        }

    }

    public boolean contain(T valor) { // se um valor esta contido na arvore
        if (buscar(valor, null) == null) return false;
        else return true;
    }

    public void inserirBB(T valor){
        GenericNode<T> no = this.raiz;
        if(type == 2){
            insercaoBB(valor, no);
            System.out.println("Valor: " + valor + " Adicionado na árvore");
        }
        if(this.type == 3){
            if (!contain(valor)) {
                insercaoBB(valor, no);
                System.out.println("Valor: " + valor + " Adicionado na árvore");
            } else {
                System.out.println("Valor: " + valor + " já existe na arvore.");
            }
        }
    }

    public void inserirBB(GenericNode<T> no,GenericNode<T> filho) { //reinserção de nó
        no = setNodeToRoot(no);
        if(filho != null){
            if (!contain(filho.getValor())) {
                insercaoBB(no, filho);
                System.out.println("Valor: " + filho.getValor() + " (re)inserido na árvore");
            } else {
                System.out.println("Valor: " + filho.getValor() + " já existe na arvore.");
            }
        }
        
    }

    public void insercaoBB(T valor, GenericNode<T> no) { // função recursiva para inserir ou reinserir um nó de busca
        //criar novos filhos
            if(this.type == 3){
                if ((int) valor < (int) no.getValor()) {
                    if (!no.hasLeft()) {
                        GenericNode<T> filho = new GenericNode<T>(valor, no);
                        no.setLeft(filho);
                    } else
                        insercaoBB(valor, no.getLeft());
                } else {
                    if (!no.hasRight()) {
                        GenericNode<T> filho = new GenericNode<T>(valor, no);
                        no.setRight(filho);
                    }else
                        insercaoBB(valor, no.getRight());
                }
    
            //inserção em arvore binaria priorizando a esquerda
            }else if(this.type == 2){
                if (!no.hasLeft()) {
                    GenericNode<T> filho = new GenericNode<T>(valor, no);
                    no.setLeft(filho);  
                }
                else if(!no.hasRight()) {
                    GenericNode<T> filho = new GenericNode<T>(valor, no);
                    no.setRight(filho);
                }
                else insercaoBB(valor, no.getLeft());
            }
    }

    //inserir uma subarvore ao inves de criar um novo nó
    public void insercaoBB(GenericNode<T> no, GenericNode<T> novoFilho) {
        if ((int) novoFilho.getValor() < (int) no.getValor()) {
            if (!no.hasLeft()) {
                novoFilho.setPai(no);
                no.setLeft(novoFilho);
            } else
                insercaoBB(no.getLeft(),novoFilho);
        }else {
            if (!no.hasRight()) {
                novoFilho.setPai(no);
                no.setLeft(novoFilho);
            }else
                insercaoBB(no.getRight(),novoFilho);
        }
    }

    public GenericNode<T> getMaior(GenericNode<T> no, String side){
        no = setNodeToRoot(no);
        if(side == null){side = "left";}
        //pegar todos os nós parentes
        ArrayList<GenericNode<T>> internos = null;
        ArrayList<GenericNode<T>> folhas = null;
        GenericNode<T> maior = new GenericNode<T>(0);
        if(side == "left"){
            internos = getInternosList(no.getLeft()); 
            folhas = getFolhas(no.getLeft()); 
        }
        else{
            internos = getInternosList(no.getRight()); 
            folhas = getFolhas(no.getRight()); 
        }
        if(folhas != null && folhas.size() != 0){
            for(int i = 0; i < folhas.size(); i++){//pegar o maior folha
                if((int) folhas.get(i).getValor() > (int) maior.getI()){
                    maior = folhas.get(i);     
                    maior.setI((int) folhas.get(i).getValor());
                }
            }
        }
        if(internos != null && internos.size() != 0 ){
            for(int i = 0; i < internos.size(); i++){//pegar o maior interno
                if((int) internos.get(i).getValor() > (int) maior.getI()){   
                    maior = internos.get(i);     
                    maior.setI((int) internos.get(i).getValor());
                }
            }
        }
        return maior;
    }

    // public int maiorValor(GenericNode<T> no, int maior){
    //     if(no.hasLeft()){
    //         if((int) no.getLeft().getValor() > maior ){
    //             maior = (int) no.getValor();
    //         }
    //         maiorValor(no.getLeft(), maior);
    //     }
    //     if(no.hasRight()){
    //         if((int) no.getRight().getValor() > maior ){
    //             maior = (int) no.getValor();
    //         }
    //         maiorValor(no.getRight(), maior);
    //     }

    //     return maior;
    // }

    public GenericNode<T> setNodeToRoot(GenericNode<T> no){
        if(no == null){
            no = this.raiz;
        }
        return no;
    }
    // if(maxCount == 0){
    // no.getFilhos().add(null);
    // no.getFilhos().add(null);
    // }
    // if(maxCount == 1){
    // no.getFilhos().add(null);
    // convertToBinary(no.getFilhos().get(0));
    // }
    // if (maxCount > 2) {
    // for (int i = 0; i < maxCount; i++) {
    // if (no.getFilhos().get(i) != null) {
    // // System.out.print(" ");
    // for (int j = 2; j < no.getFilhos().size(); j++) {
    // no.getFilhos().remove(j);
    // System.out.println("cusasds");
    // }
    // // System.out.println(no.getFilhos().get(i).getValor());
    // convertToBinary(no.getFilhos().get(i));
    // }
    // }
    // }

    public void clear() { // limpar variaveis auxiliares para evitar bugs (utilizar no metodo main)
        // folhas.clear();
        // internosList.clear();
        // noResultado = null
        // altura_arvore = 0;

    }

    // if(maxCount == 0){
    // no.getFilhos().add(null);
    // no.getFilhos().add(null);
    // }
    // if(maxCount == 1){
    // no.getFilhos().add(null);
    // convertToBinary(no.getFilhos().get(0));
    // }
    // if (maxCount > 2) {
    // for (int i = 0; i < maxCount; i++) {
    // if (no.getFilhos().get(i) != null) {
    // // System.out.print(" ");
    // for (int j = 2; j < no.getFilhos().size(); j++) {
    // no.getFilhos().remove(j);
    // System.out.println("cusasds");
    // }
    // // System.out.println(no.getFilhos().get(i).getValor());
    // convertToBinary(no.getFilhos().get(i));
    // }
    // }
    // }

    // if(maxCount == 0){
    // no.getFilhos().add(null);
    // no.getFilhos().add(null);
    // }
    // if(maxCount == 1){
    // no.getFilhos().add(null);
    // convertToBinary(no.getFilhos().get(0));
    // }
    // if (maxCount > 2) {
    // for (int i = 0; i < maxCount; i++) {
    // if (no.getFilhos().get(i) != null) {
    // // System.out.print(" ");
    // for (int j = 2; j < no.getFilhos().size(); j++) {
    // no.getFilhos().remove(j);
    // System.out.println("cusasds");
    // }
    // // System.out.println(no.getFilhos().get(i).getValor());
    // convertToBinary(no.getFilhos().get(i));
    // }
    // }
    // }

}
