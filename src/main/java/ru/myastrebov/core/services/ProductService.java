package ru.myastrebov.core.services;

import ru.myastrebov.core.Product;

import java.util.List;

/**
 * Сервис для работы с продуктами.
 *
 * @author Maxim
 */
public interface ProductService {

    /**
     * Получение списка всех продуктов.
     * @return список продуктов
     */
    List<Product> getAllProducts();

    /**
     * Создание нового продукта.
     * @param product продукт для создания
     * @return продукт с заданным идентификатором, если создание прошло успешно.
     */
    Product createProduct(Product product);

    /**
     * Поиск продукт по идентификатору.
     * @param productId идентифифкатор продукта
     * @return продукт, если найден, иначе null.
     */
    Product findById(Long productId);

    /**
     * Обновление информации продукта.
     * @param productId идентифифкатор продукта
     * @param productForUpdate контент для обновления
     * @return обновлённый продукт
     */
    Product updateProduct(Long productId, Product productForUpdate);

    /**
     * Удаление продукта
     * @param productId идентифифкатор продукта
     */
    void delete(Long productId);
}
