<%@page contentType="text/html" pageEncoding="UTF-8" %> 
<%@page import="java.util.List"%> 
<%@page import="com.freshbowl.model.pojos.InventoryItem"%> 
<%@page import="com.freshbowl.model.pojos.output.InventoryOut"%> 
<!DOCTYPE html>
<html lang="es">

<head>
    <title>The Fresh Bowl (Inventario)</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="css/index/style.css">
    <script src="js/bootstrap/bootstrap.bundle.min.js"></script>
</head>

<body class="bg-light">

    <header>
        <nav class="navbar fixed-top navbar-dark bg-success navbar-expand-lg">
            <div class="container-fluid">
                <a class="navbar-brand" href="index.jsp">
                    <div class="img-panel" id="logo-panel">
                        <img src="imgs/logo-w.png" alt="" class="d-inline-block align-text-top">
                    </div>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav-options"
                    aria-controls="nav-options" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="nav-options">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 w-100 justify-content-end">
                        <li class="nav-item text-center">
                            <a class="nav-link active" href="index.jsp">Menú</a>
                        </li>
                        <li class="nav-item text-center">
                            <a class="nav-link" aria-current="page" href="recipes">Recetas</a>
                        </li>
                        <li class="nav-item text-center">
                            <a class="nav-link" href="inventory">Inventario</a>
                        </li>

                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <section id="inventory">
        <div class="full-container screen-container">
            <h2 class="section-title mt-5">Inventario</h2>

            <div class="input-group mb-5 py-4 px-5 bg-success">
                <input type="text" class="form-control mx-3" placeholder="Buscar..." aria-label="Buscar" aria-describedby="search-btn">
                <div class="input-group-append mx-3 w-25">
                    <button class="btn btn-primary w-100" type="button" id="search-btn">Buscar</button>
                </div>
            </div>

            <div class="row menu-row">
                
            <%
                List<InventoryOut> inventoryList = (List<InventoryOut>) request.getAttribute("inventory");

                if (inventoryList != null) {
                    for (InventoryOut item : inventoryList) {
            %>

                <div class="col col-12 col-xxl-3 col-lg-4 col-md-6">
                    <div class="card">
                        <div class="card-img-panel bg-dark">
                            <img src="<%= item.getImg() %>" class=" bg-dark" alt="...">

                        </div>
                        <div class="card-body">

                            <h5 class="card-title"><%= item.getItemName() %></h5>
                        
                            <p class="card-text">
                                Existencia: <span class="pricing-buttons"><%= item.getQuantity() %> x [ <%= item.getUnitName() %> ]</span>
                            </p>

                            <p class="card-text">
                                Precio actual: <span class="pricing-buttons">$ <%= item.getPrice() %> / <%= item.getUnitName() %></span>
                            </p>

                            <p class="card-text">
                                Fecha de adquisición: <span class="pricing-buttons"><%= item.getAcquisitionDate() %></span>
                            </p>
                            
                        </div>
                        <div class="card-body card-pricing">
                            <div class="row pricing-buttons">
                                <div class="btn-group" role="group" aria-label="pricing buttons">
                                    <a type="button" href="#" class="btn btn-outline-secondary">
                                        <p class="my-0">Actualizar</p>
                                    </a>
                                   
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        
            <%
                    }
                } else {
                    out.println("La lista de inventario es nula.");
                }
            %>
        </div>
    </section>
    
</body>
</html>