package com.example.indoor.service;

import com.example.indoor.controller.ProductController;
import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.entity.Product;
import com.example.indoor.mapper.ProductMapper;
import com.example.indoor.mapper.StockNoticeMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    StockNoticeMapper stockNoticeMapper;

    public final String NO_IMAGE_FILE_PATH = "/img/no-image.png";
    public final String PRODUCT_IMAGE_PATH = "./src/main/resources/static/img/product/";
    public final String IMAGE_RELATIVE_PATH = "/img/product/";

    /*
     * 出品送品一覧を取得
     */
    public List<ProductForm> findProductDisplay(int account_id) {
        List<Product> results = productMapper.findProductDisplay(account_id);
        List<ProductForm> products = setForm(results);
        return products;
    }
    /*
     * 主キー指定で商品レコードを取得
     */
    public ProductForm findProduct(int id) {
        List<Product> results = new ArrayList<>();
        results.add(productMapper.findProductDetail(id));
        List<ProductForm> products = setForm(results);

        return products.get(0);
    }
    /*
     * Product entityをProduct formにコピー
     */
    private List<ProductForm> setForm(List<Product> entities) {
        List<ProductForm> forms = new ArrayList<>();

        for (Product entity : entities) {
            ProductForm form = new ProductForm();
            // ファイルパスを修正
            if (StringUtils.isBlank(entity.getImagePass())) {
                entity.setImagePass(NO_IMAGE_FILE_PATH);
            } else {
                entity.setImagePass(IMAGE_RELATIVE_PATH + entity.getImagePass());
            }
            BeanUtils.copyProperties(entity, form);
            forms.add(form);
        }
        return forms;
    }

    /*
     * 在庫数更新と在庫0チェック
     */
    public void updateProductStock(int number, int productId) {
        // 商品の在庫更新
        productMapper.updateProductStock(number, productId);
        // 商品の在庫が0かチェック
        boolean isStockZero = productMapper.checkStockIsZero(productId);
        if (isStockZero) { //在庫が0の場合はstock_noticesにINSERT文を作る
            stockNoticeMapper.insertStockNotice(productId);
        }
    }
    /*
     * 商品レコード追加
     */
    public void insertProduct(ProductForm productForm) {
        Product saveProduct = setProductEntity(productForm);
        productMapper.insertProduct(saveProduct);
    }
    /*
     * Product formをEntityにコピー
     */
    private Product setProductEntity(ProductForm form){
        Product entity = new Product();
        // ファイルパスを修正
        if (form.getImagePass().equals(NO_IMAGE_FILE_PATH)) {
            form.setImagePass(null);
        } else {
            int pathIndex = form.getImagePass().lastIndexOf(IMAGE_RELATIVE_PATH);
            if (pathIndex >= 0) {
                form.setImagePass(form.getImagePass().substring(IMAGE_RELATIVE_PATH.length()).toLowerCase());
            }
        }
        BeanUtils.copyProperties(form, entity);
        return entity;
    }
    /*
     * 商品レコードを更新
     */
    public void updateProduct(ProductForm productForm) {
        Product saveProduct = setProductEntity(productForm);
        productMapper.updateProduct(saveProduct);
    }
    // ファイルをサーバーに保存
    public void saveFile(MultipartFile file, String fileName) throws IOException {
        Path uploadFile = Paths.get(PRODUCT_IMAGE_PATH + fileName);
        try (OutputStream os = Files.newOutputStream(uploadFile, StandardOpenOption.CREATE)) {
            byte[] bytes = file.getBytes();
            os.write(bytes);
        } catch (IOException e) {
            //エラー処理は省略
        }
    }
    //　ファイル名にタイムスタンプを付与
    public String getUploadFileName(String fileName) {

        return fileName + "_" +
                DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
                        .format(LocalDateTime.now())
                + getExtension(fileName);
    }
    // ファイルの拡張子を取得
    private String getExtension(String filename) {
        int dot = filename.lastIndexOf(".");
        if (dot > 0) {
            return filename.substring(dot).toLowerCase();
        }
        return "";
    }
}
