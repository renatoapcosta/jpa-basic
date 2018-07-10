package br.com.ractecnologia.curso.book;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.ractecnologia.curso.book.Book;


public class BookTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void initEntityManager() throws Exception {
        emf = Persistence.createEntityManagerFactory("chapter02PU");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() throws SQLException {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Before
    public void initTransaction() {
        tx = em.getTransaction();
    }


    @Test
    public void shouldCreateABook() throws Exception {

    	Book book = new Book();
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setPrice(12.5);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);
        
        tx.begin();
        em.persist(book);
        tx.commit();
        
        assertNotNull("ID should not be null", book.getId());
        
        Query  query = em.createNamedQuery("findAllBooks");
        
        @SuppressWarnings("unchecked")
		List<Book> books = query.getResultList();
        assertEquals(1, books.size());
    }
}