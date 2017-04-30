<?php
    $con = mysqli_connect("localhost", "id1187140_prism_aidh", "123456", "id1187140_prism_db");
    
    $address = $_POST["address"];
    $body = $_POST["body"];
    

    $statement = mysqli_prepare($con, "INSERT INTO prism_sms (address, body) VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, "ss", $address, $body);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>
