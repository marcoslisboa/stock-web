package br.com.webapp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import br.com.webapp.dao.ProductDAO;
import br.com.webapp.dto.BasicResponse;
import br.com.webapp.entities.Product;
import lombok.extern.java.Log;

@Log
@WebServlet(value = "/api/product")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();

        if (StringUtils.isNotBlank(id)) {
            Product product = productDAO.findById(Long.valueOf(id));

            if (product != null) {
                out.print(gson.toJson(product));
            } else {
                out.print(gson.toJson(
                    BasicResponse
                        .builder()
                        .status(404)
                        .message("Produto não encontrado.")
                        .build())
                );
            }

        } else {
            out.print(gson.toJson(productDAO.findAll()));
        }

        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = gson.fromJson(readJson(req), Product.class);
        PrintWriter out = resp.getWriter();

        if (product.getId() != null) {
            out.print(gson.toJson(productDAO.update(product)));

        } else {
            out.print(gson.toJson(productDAO.insert(product)));
        }
        out.flush();

    }

    private String readJson(HttpServletRequest req) {
        StringBuilder jb = new StringBuilder();
        String line = null;

        try {
            BufferedReader reader = req.getReader();

            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }

        } catch (Exception e) {
            log.warning(e.getMessage());
        }

        return jb.toString();

    }
}
