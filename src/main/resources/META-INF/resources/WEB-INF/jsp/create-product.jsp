<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Page</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

</head>
<body>
  <form:form action="add-product" method="post" modelAttribute="product">
    <form:hidden  path="id" class="form-control" id="ProductId" placeholder="" />
    <div class="container">
      <div class="mb-3">
        <label for="ProductName" class="form-label">Product Name</label>
        <form:input path="name" type="text" class="form-control" id="ProductName" placeholder="" required="required" />
        <form:errors path="name" cssClass="text-danger" />
      </div>
      <div class="mb-3">
        <label for="ProductDescription" class="form-label">Product description</label>
        <textarea name="description" class="form-control" id="ProductDescription" rows="3"></textarea>
      </div>
      <div class="mb-3">
        <label for="ProductQuantity" class="form-label">Quantity</label>
        <form:input path="quantity" type="number" class="form-control" id="ProductQuantity" placeholder="" />
        <form:errors path="quantity" cssClass="text-danger" />
      </div>
      <div class="mb-3">
        <label for="ProductPrice" class="form-label">Price</label>
        <form:input path="price" type="text" class="form-control" id="ProductPrice" placeholder="" />
        <form:errors path="price" cssClass="text-danger" />
      </div>
      <button type="submit" class="btn btn-primary">Add</button>
    </div>
  </form:form>
</body>
</html>