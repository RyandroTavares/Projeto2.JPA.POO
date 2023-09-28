
package com.br.biblioteca.JPA_PostgreSQL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.EditoraDAO;
import dao.LivroDAO;
import model.Autor;
import model.Categoria;
import model.Editora;
import model.Livro;
import util.JPAUtil;

public class App {
    public static void main(String[] args) throws IOException {

        EntityManager em = JPAUtil.getEntityManager();

        LivroDAO livroDAO = new LivroDAO(em);
        EditoraDAO editoraDAO = new EditoraDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        AutorDAO autorDAO = new AutorDAO(em);

        Categoria categoria1 = new Categoria("Ação");
        Categoria categoria2 = new Categoria("Comédia");
        categoriaDAO.cadastrar(categoria1);
        categoriaDAO.cadastrar(categoria2);

        Editora editora1 = new Editora("Aleph");
        Editora editora2 = new Editora("Rocco");
        editoraDAO.cadastrar(editora1);
        editoraDAO.cadastrar(editora2);

        Autor autor1 = new Autor("Lucas Lschlestein");
        Autor autor2 = new Autor("André Santos");
        autorDAO.cadastrar(autor1);
        autorDAO.cadastrar(autor2);

        List<Autor> autores = new ArrayList<>();
        autores.add(autor1);
        autores.add(autor2);

        Livro harryPotter = new Livro("Harry Potter", 1999, 123456789, categoria1, editora1, autores);
        Livro piorQueTaNaoFica = new Livro("Pior que tá não fica", 2015, 15198487, categoria2, editora2, autores);
        Livro oEspadachimDeCarvao = new Livro("O Espadachim de carvão", 2010, 15198487, categoria1, editora2, autores);
        livroDAO.cadastrar(oEspadachimDeCarvao);
        livroDAO.cadastrar(piorQueTaNaoFica);
        livroDAO.cadastrar(harryPotter);

        List<Livro> todosLivros = livroDAO.buscarTodosOsLivros();

        for (Livro livro : todosLivros) {
            System.out.println("\nID do Livro: " + livro.getId());
            System.out.println("ISBN: " + livro.getISBN());
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Ano de lançamento: " + livro.getAno());
            System.out.println("Categoria: " + livro.getCategoria());
            System.out.println("Editora: " + livro.getEditora());
            System.out.println("Autores: " + livro.getAutores());
        }

       Livro atualizaLivro = em.find(Livro.class, 1l);
       System.out.println("LIVRO: " + atualizaLivro);

       atualizaLivro.setTitulo("Senhor dos Anéis");
       livroDAO.atualizar(atualizaLivro);

       Livro livro = livroDAO.buscarPorId(1l);
       System.out.println("------------------");
       System.out.println("\n" + livro.getId());
       System.out.println(livro.getISBN());
       System.out.println(livro.getTitulo());
       System.out.println(livro.getAno());
       System.out.println(livro.getCategoria().getNome());
       System.out.println(livro.getEditora().getNome());
       System.out.println("Autores: " + livro.getAutores());

       livroDAO.remover(livro);

    em.getTransaction().begin();
    em.getTransaction().commit();
    em.close();
    }
}