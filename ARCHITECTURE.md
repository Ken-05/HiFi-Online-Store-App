# HiFi Online Store Application Architecture
The HiFi store application consists of a presentation, logic/business, and database layer.

<span style="text-decoration:underline;">The presentation layer consists of</span>



* SearchProductActivity
    * Allows customers to see a list of products based on their search
* HomeActivity 
    * A GUI for customers to click on featured products, a search bar, and a top menu.
* CartActivity
    * A GUI for having a list of products to checkout
* CartItem
    * A GUI to show items in the cart
* EditProfileActivity
    * Makes a GUI to edit profile attributes
* ProductDescriptionActivity
    * A GUI for the customer that includes information about the product, such as product description and product name
* MenuActivity
    * A standardized GUI for a top menu
* OrderHistoryActivity
    * A GUI to show a user’s order history
* UserCreateAccountActivity
    * A GUI to allow users to create an account
* UserSignInActivity
    * Allows users to log into their account
* WishListActivity
    * A GUI that provides users a list of products they would like to buy in the future

<span style="text-decoration:underline;">The logic/business layer consists of</span>



* SearchProducts
    * Includes method that reads user input from the search bar
        * Uses that input to find keywords to search product information for and return a list of SearchResult by order of relevance
* AccessProducts
    * Includes methods that return, insert, update, and delete products
* AccessCart
    * Includes methods that return and save a cart
* AccessOrderHistory
    * Includes methods that return and save an order
* AccessUsers
    * Includes methods that return,insert,update and delete users.
* UserService
    * Keeps track of the current user

<span style="text-decoration:underline;">The database layer consists of </span>



* ProductsPersistanceHSQLDB
    * Stores products and their information
* CartIemPersistenceHSQLDB
    * Stores a user’s product selection
* OrderHistoryPersistence
    * Stores user’s order history
* UserPersistence
    * Stores users and their information