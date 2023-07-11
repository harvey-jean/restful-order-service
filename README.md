***********************************************************
***********************************************************
THIS IS A RESTFUL API FOR MANAGING AN ONLINE ORDERING SYSTEM
************************************************************

Below are based relationship between 
- products, 
- categories, 
- customers, 
- orders, 
- ratings:
***********************************************************
    1- Products and Categories:
        One product can belong to only one category.
        One category can have multiple products.
        This relationship is a many-to-one relationship, as many products can be associated with a single category.

    2- Products and Customers:
        One customer can rate multiple products.
        One product can be rated by multiple customers.
        This relationship is a many-to-many relationship, as multiple customers can rate multiple products.

    3- Products and Orders:
        One order can contain multiple products.
        One product can be present in multiple orders.
        This relationship is a many-to-many relationship, as a product can be included in multiple orders and an order can have multiple products.

    4- Products and Ratings:
        One product can have multiple ratings.
        One rating is associated with a single product.
        This relationship is a one-to-many relationship, as one product can have many ratings.

    5- Customers and Orders:
        One customer can have multiple orders.
        One order belongs to a single customer.
        This relationship is a one-to-many relationship, as one customer can have multiple orders.

    6- Customers and Ratings:
        One customer can rate multiple products.
        One rating is associated with a single customer.
        This relationship is a one-to-many relationship, as one customer can have multiple ratings.

***********************************************************
***********************************************************
Required tools to run the application
***********************************************************
- Jdk 17
- javac release 17
- Libraries for Springboot V3.1.1
- Used Maven wrapper
- Tested on MysQL 8, H2
