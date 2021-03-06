<?php
$host = "localhost";
$username = "root";
$password = "";
$dbname = "db_foodiepedia";

$conn = mysqli_connect($host, $username, $password, $dbname);
if (!$conn) {
    die("Connection Failed: " . mysqli_connect_error());
}

//buat ngeset function
switch ($_POST["func"]) {
    case "regis":
        register($conn);
        break;
    case "login":
        login($conn);
        break;
    case "getuser":
        getuser($conn);
        break;
    case "getalluser":
        getalluser($conn);
        break;
    case "setban":
        setban($conn);
        break;
    case "updateuser":
        updateuser($conn);
        break;
    case "getapprovedings":
        getingredients($conn);
        break;
    case "addresep":
        addrecipe($conn);
        break;
    case "addresepings":
        addingtorecipe($conn);
        break;
    case "adding":
        addingredient($conn);
        break;
    case "getresep":
        getresep($conn);
        break;
    case "deleteresep":
        deleteresep($conn);
        break;
    case "getresepdets":
        getresepdetail($conn);
        break;
    case "fol":
        unorfollow($conn);
        break;
    case "getrating":
        getrating($conn);
        break;
    case "insertrating":
        insertrating($conn);
        break;
    case "updaterating":
        updaterating($conn);
        break;
    case "getresepuser":
        getresepuser($conn);
        break;
    case "admingetresepdetail":
        admingetdetailresep($conn);
        break;
    case "getfavorites":
        getfavorites($conn);
        break;
    case "insertfavorites":
        insertfavorites($conn);
        break;
    case "searchresep":
        cariresep($conn);
        break;
    case "ret":
        getratingavg($conn);
        break;
    case "getlistfavorites":
        getListFavorites($conn);
        break;
    case "getbahan":
        getbahan($conn);
        break;
    case "getbahanadmin":
        getbahanadmin($conn);
        break;
    case "accbahanadmin":
        accbahanadmin($conn);
        break;
    case "getmessages":
        getmess($conn);
        break;
    case "delmessages":
        delmess($conn);
        break;
    case "havemsg":
        havemessage($conn);
        break;
    case "makemsg":
        makemessage($conn);
        break;
    default:
        echo "function tidak ada";
        break;
}

function register($conn)
{
    $username = $_POST["user"];
    $password = $_POST["pass"];
    $name = $_POST["name"];
    $sql = "SELECT count(*) FROM user WHERE user_username = '$username'";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    if ($result[0] == 0) {
        $sql_insert = "INSERT INTO user (user_username,user_viewedname,user_password) VALUES ('$username','$name', '$password')";
        $query = mysqli_query($conn, $sql_insert);
        if ($query) {
            $message = "Register Berhasil!";
        } else {
            $message = "Register Gagal";
        }
    } else {
        $message = "Pengguna sudah ada!";
    }
    echo $message;
}

function login($conn)
{
    $username = $_POST["user"];
    $password = $_POST["pass"];
    $sql = "SELECT * FROM user WHERE user_username = '$username'";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    if ($result) {
        if ($password == $result[3]) {
            $response["userid"] = $result[0];
            $response["message"] = "Login Berhasil!";
            $response["isadmin"] = $result[4];
            $response["ban"] = $result[5];
        } else {
            $response["userid"] = -1;
            $response["message"] = "Password Salah";
        }
    } else {
        $response["userid"] = -1;
        $response["message"] = "User Tidak Ditemukan";
    }
    echo json_encode($response);
}

function getuser($conn)
{
    $id = $_POST["userid"];
    $sql = "SELECT * FROM user WHERE user_id = $id";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    $response["nama"] = $result[2];
    $response["pass"] = $result[3];
    echo json_encode($response);
}

function getalluser($conn)
{
    $sql = "SELECT * FROM user WHERE user_isadmin = 0";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $arruser = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["id"] = $row["user_id"];
            $data["nama"] = $row["user_viewedname"];
            $data["pass"] = $row["user_password"];
            $data["is_banned"] = $row["user_isbanned"];
            $arruser[$ctr] = $data;
            $ctr++;
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["datauser"] = $arruser;
    } else {
        $response["code"] = -3;
        $response["message"] = "No Data";
    }
    echo json_encode($response);
}

function setban($conn)
{
    $id = $_POST["userid"];
    $ban = $_POST["isbanned"];
    if ($ban == 0) {
        mysqli_query($conn, "UPDATE user SET user_isbanned=1 WHERE user_id=$id");
        $response["message"] = "Ban Berhasil";
    } else {
        mysqli_query($conn, "UPDATE user SET user_isbanned=0 WHERE user_id=$id");
        $response["message"] = "Unban Berhasil";
    }
    echo json_encode($response);
}

function updateuser($conn)
{
    $id = $_POST["userid"];
    $nama = $_POST["name"];
    $succ = 0;
    if (mysqli_query($conn, "UPDATE user SET user_viewedname='$nama' WHERE user_id=$id")) {
        $succ += 1;
    }
    if ($_POST["ispass"] == "y") {
        $pass = $_POST["pass"];
        if (mysqli_query($conn, "UPDATE user SET user_password='$pass' WHERE user_id=$id")) {
            $succ += 1;
        }
    }
    $response["code"] = $succ;
    echo json_encode($response);
}

function getingredients($conn)
{
    if ($_POST["verified"] == 0) {
        $result = mysqli_query($conn, "SELECT * FROM bahans ORDER BY bahan_nama ASC");
    } else {
        $result = mysqli_query($conn, "SELECT * FROM bahans WHERE bahan_isapproved = 1 ORDER BY bahan_nama ASC");
    }
    while ($row = mysqli_fetch_row($result)) {
        $isi["id"] = $row[0];
        $isi["nama"] = $row[1];
        $isi["app"] = $row[2];
        $bahan[] = $isi;
    }
    echo json_encode($bahan);
}

function addrecipe($conn)
{
    $id = $_POST["id"];
    $nm = $_POST["nama"];
    $desc = $_POST["desc"];
    $ada = $_POST["ada"];
    mysqli_query($conn, "INSERT INTO reseps (resep_nama,user_id,resep_desc,resep_isapproved) VALUES ('$nm',$id,'$desc',$ada)");
    echo mysqli_fetch_row(mysqli_query($conn, "SELECT * FROM reseps WHERE resep_nama = '$nm'"))[0];
}

function addingredient($conn)
{
    $nm = $_POST["nama"];
    mysqli_query($conn, "INSERT INTO bahans (bahan_nama,bahan_isapproved) VALUES ('$nm',0)");
}

function addingtorecipe($conn)
{
    $id = $_POST["id"];
    $nm = $_POST["nama"];
    $jum = $_POST["jum"];
    $idbahan = mysqli_fetch_row(mysqli_query($conn, "SELECT * FROM bahans WHERE bahan_nama = '$nm'"))[0];
    mysqli_query($conn, "INSERT INTO bahanresep VALUES ($idbahan,$id,'$jum')");
    echo mysqli_fetch_row(mysqli_query($conn, "SELECT count(*) FROM bahanresep WHERE resep_id = $id"))[0];
}

function getresep($conn)
{
    $id =  $_POST["id"];
    if ($id <= 0) {
        $sql = "SELECT * FROM reseps r, USER u WHERE u.user_id = r.user_id AND resep_isapproved = 1";
    } else {
        $sql = "SELECT * FROM reseps r, USER u ,user_follows uf
        WHERE u.user_id = r.user_id
        AND uf.user_id_follower = $id
        AND uf.user_id = r.user_id
        AND r.resep_isapproved = 1";
    }
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $reseps = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["resep_id"] = $row["resep_id"];
            $data["resep_nama"] = $row["resep_nama"];
            $data["resep_desk"] = $row["resep_desc"];
            $data["chef_name"] = $row["user_viewedname"];
            $data["chef_id"] = $row["user_id"];
            $data["resep_isapproved"] = $row["resep_isapproved"];
            $reseps[$ctr] = $data;
            $ctr++;
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["dataresep"] = $reseps;
    } else {
        $response["code"] = -1;
        $response["message"] = "Tidak Ada Request Resep";
    }
    echo json_encode($response);
}

function deleteresep($conn)
{
    $id = $_POST["id"];
    $sql = "DELETE from reseps WHERE resep_id = $id";
    $query1 = mysqli_query($conn, "SELECT * FROM reseps WHERE resep_id = $id");
    while ($row = mysqli_fetch_row($query1)) {
        mysqli_query($conn, "INSERT INTO user_message (user_message,user_from, user_to,message_time) VALUES ('Menu " . $row[2] . " anda telah dihapus oleh Admin',0, $row[1],'" . date("Y-m-d H:i:s") . "')");
    }
    $result = mysqli_query($conn, $sql);
    if ($result) {
        $response["code"] = 1;
        $response["message"] = "Resep Berhasil Dihapus";
    } else {
        $response["code"] = -1;
        $response["message"] = "Resep Gagal Dihapus";
    }
    echo json_encode($response);
}

function getresepdetail($conn)
{
    $id = $_POST["user"];
    $idfud = $_POST["food"];
    $idchef = $_POST["chef"];

    $sql = "SELECT * FROM user_favorites WHERE user_id = $id and resep_id = $idfud";
    if (mysqli_num_rows(mysqli_query($conn, $sql)) > 0) $response["fav"] = 1;
    else $response["fav"] = 0;

    $sql = "SELECT * FROM user_follows WHERE user_id = $idchef and user_id_follower = $id";
    if (mysqli_num_rows(mysqli_query($conn, $sql)) > 0) $response["fol"] = 1;
    else $response["fol"] = 0;

    $sql = "SELECT * FROM user_rating WHERE user_id = $id and resep_id = $idfud";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) $response["rat"] = mysqli_fetch_array($result)[2];
    else $response["rat"] = 0;

    echo json_encode($response);
}

function admingetdetailresep($conn)
{
    $idresep = $_POST["idresep"];

    $sql = "SELECT AVG(rating) FROM user_rating WHERE resep_id = $idresep";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    if ($result[0] == null) {
        $response["rating"] = 0;
    } else {
        $response["rating"] = $result[0];
    }

    $sql = "SELECT * FROM reseps r, user u WHERE r.user_id = u.user_id AND r.resep_id = $idresep";
    $result = mysqli_fetch_array(mysqli_query($conn, $sql));

    if ($result) {
        $data = array();
        $data["resep_id"] = $result["resep_id"];
        $data["resep_nama"] = $result["resep_nama"];
        $data["resep_desk"] = $result["resep_desc"];
        $data["chef_name"] = $result["user_viewedname"];
        $data["chef_id"] = $result["user_id"];
        $data["resep_isapproved"] = $result["resep_isapproved"];
        $response["resep"] = $data;

        $sql = "SELECT * FROM bahanresep br, bahans b WHERE br.bahan_id = b.bahan_id AND br.resep_id = $idresep";
        $result = mysqli_query($conn, $sql);
        if (mysqli_num_rows($result) > 0) {
            $data = array();
            $bahans = array();
            $ctr = 0;
            while ($row = mysqli_fetch_array($result)) {
                $data["bahan_id"] = $row["bahan_id"];
                $data["bahan_nama"] = $row["bahan_nama"];
                $data["bahan_isapproved"] = $row["bahan_isapproved"];
                $data["qty"] = $row["qty"];
                $bahans[$ctr] = $data;
                $ctr++;
            }
            mysqli_free_result($result);
            $response["code"] = 1;
            $response["message"] = "Get Data Successful";
            $response["bahan"] = $bahans;
        } else {
            $response["code"] = -2;
            $response["message"] = "Get Bahan Failed";
        }
    } else {
        $response["code"] = -1;
        $response["message"] = "Get Resep Failed";
    }
    echo json_encode($response);
}

function unorfollow($conn)
{
    $id = $_POST["user"];
    $idchef = $_POST["chef"];

    if (mysqli_num_rows(mysqli_query($conn, "SELECT * FROM user_follows WHERE user_id = $idchef and user_id_follower = $id")) > 0) {
        $insert = 0;
        $sql = "DELETE FROM user_follows WHERE user_id = $idchef and user_id_follower = $id";
    } else {
        $insert = 1;
        $sql = "INSERT INTO user_follows VALUES ($idchef,$id)";
    }
    mysqli_query($conn, $sql);
    echo $insert;
}

function getrating($conn)
{
    $id = $_POST["user"];
    $idresep = $_POST["resep"];
    $result = mysqli_query($conn, "SELECT rating FROM user_rating WHERE resep_id = $idresep and user_id = $id");
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        while ($row = mysqli_fetch_array($result)) {
            $data["rating"] = $row["rating"];
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["rating"] = $data;
    } else {
        $response["code"] = 2;
        $response["message"] = "Get Data Failed";
    }
    echo json_encode($response);
}

function getratingavg($conn)
{
    $idresep = $_POST["resep"];
    $result = mysqli_query($conn, "SELECT rating FROM user_rating WHERE resep_id = $idresep");
    if (mysqli_num_rows($result) > 0) {
        $rating = 0;
        $jum = 0;
        while ($row = mysqli_fetch_array($result)) {
            $rating += $row[0];
            $jum++;
        }
        mysqli_free_result($result);
        $rating = $rating * 1.0 / $jum;
        $rating = round($rating * 2) * 1.0 / 2;
    } else {
        $rating = 0;
    }
    echo json_encode($rating);
}

function insertrating($conn)
{
    $userid = $_POST['userid'];
    $resepid = $_POST['resepid'];
    $rating = $_POST['rating'];
    $sql = "INSERT INTO user_rating values($userid,$resepid,$rating)";
    $flag = mysqli_query($conn, $sql);
    if ($flag) {
        echo "Insert Data Successful";
    } else {
        echo "Insert Data Failed";
    }
}

function updaterating($conn)
{
    $userid = $_POST['userid'];
    $resepid = $_POST['resepid'];
    $rating = $_POST['rating'];
    $sql = "UPDATE user_rating SET rating = $rating where user_id = $userid AND resep_id = $resepid;";
    $flag = mysqli_query($conn, $sql);
    if ($flag) {
        echo "Update Data Successful";
    } else {
        echo "Update Data Failed";
    }
}

function getresepuser($conn)
{
    $userid = $_POST['userid'];
    $sql = "SELECT * FROM reseps r, USER u WHERE u.user_id = r.user_id and u.user_id = $userid";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $reseps = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["resep_id"] = $row["resep_id"];
            $data["resep_nama"] = $row["resep_nama"];
            $data["resep_desk"] = $row["resep_desc"];
            $data["chef_name"] = $row["user_viewedname"];
            $data["chef_id"] = $row["user_id"];
            $data["resep_isapproved"] = $row["resep_isapproved"];
            $reseps[$ctr] = $data;
            $ctr++;
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["dataresep"] = $reseps;
    } else {
        $response["code"] = -1;
        $response["message"] = "Tidak Ada Request Resep";
    }
    echo json_encode($response);
}

function getfavorites($conn)
{
    $user_id = $_POST["user_id"];
    $resep_id = $_POST["resep_id"];
    $query = "SELECT * FROM reseps WHERE user_id = $user_id AND resep_id = $resep_id";
    $result = mysqli_query($conn, $query);
    if (mysqli_fetch_row($result) > 0) {
        $query = "SELECT * FROM USER_FAVORITES WHERE USER_ID = $user_id and RESEP_ID = $resep_id";
        $result = mysqli_query($conn, $query);
        if (mysqli_num_rows($result) > 0) {
            echo "favorites";
        } else {
            echo "notyet";
        }
    } else {
        echo "resep sendiri";
    }
}

function insertfavorites($conn)
{
    $query = $_POST["query"];
    $user_id = $_POST["user_id"];
    $resep_id = $_POST["resep_id"];
    $return = "";
    if ($query == "insert") {
        $query1 = "INSERT INTO user_favorites VALUES($user_id,$resep_id)";
        $result = mysqli_query($conn, $query1);
        if ($result) {
            $return = "insert berhasil";
        } else {
            $return = "insert gagal";
        }
    } else {
        $query1 = "DELETE FROM user_favorites WHERE user_id = $user_id AND resep_id = $resep_id";
        $result = mysqli_query($conn, $query1);
        if ($result) {
            $return = "delete berhasil";
        } else {
            $return = "delete gagal";
        }
    }
    echo $return;
}
function getListFavorites($conn)
{
    $user_id = $_POST["userid"];
    $query = "SELECT uf.resep_id,r.resep_nama,r.resep_desc,u.user_viewedname,u.user_id,r.resep_isapproved
    FROM USER u LEFT JOIN user_favorites uf ON u.user_id = uf.user_id LEFT JOIN reseps r ON uf.resep_id = r.resep_id 
    WHERE u.user_id = $user_id";
    $result = mysqli_query($conn, $query);
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $reseps = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["resep_id"] = $row["resep_id"];
            $data["resep_nama"] = $row["resep_nama"];
            $data["resep_desk"] = $row["resep_desc"];
            $data["chef_name"] = $row["user_viewedname"];
            $data["chef_id"] = $row["user_id"];
            $data["resep_isapproved"] = $row["resep_isapproved"];
            $reseps[$ctr] = $data;
            $ctr++;
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["dataresep"] = $reseps;
    } else {
        $response["code"] = -1;
        $response["message"] = "Tidak Ada Request Resep";
    }
    echo json_encode($response);
}

function cariresep($conn)
{
    $kword = $_POST["key"];
    $reseps = json_decode($_POST["reseps"]);
    $sql = "SELECT r.* , u.user_viewedname,COUNT(r.resep_nama)  AS jumlah 
    FROM reseps r, bahanresep b ,USER u 
    WHERE r.resep_id = b.resep_id 
    AND r.user_id = u.user_id 
    AND resep_isapproved = 1";
    if (count($reseps) > 0) {
        $sql = $sql . " AND (";
        for ($i = 0; $i < count($reseps); $i++) {
            $sql = $sql . "b.bahan_id = $reseps[$i] OR ";
        }
        $sql = substr($sql, 0, strlen($sql) - 4) . ") ";
    }
    $sql = $sql . "AND r.resep_nama LIKE '%$kword%' 
    GROUP BY r.resep_id 
    HAVING jumlah >= " . count($reseps);
    $result = mysqli_query($conn, $sql);
    while ($row = mysqli_fetch_array($result)) {
        $data["resep_id"] = $row["resep_id"];
        $data["resep_nama"] = $row["resep_nama"];
        $data["resep_desk"] = $row["resep_desc"];
        $data["chef_name"] = $row["user_viewedname"];
        $data["chef_id"] = $row["user_id"];
        $data["resep_isapproved"] = $row["resep_isapproved"];
        $response[] = $data;
    }
    if (isset($response)) {
        echo json_encode($response);
    } else {
        echo "no";
    }
}
function getbahan($conn)
{
    $resep_id = $_POST["resep_id"];
    $query = "SELECT b.bahan_nama,br.qty 
    FROM bahanresep br LEFT JOIN bahans b ON br.bahan_id = b.bahan_id
    WHERE br.resep_id = $resep_id";
    $result = mysqli_query($conn, $query);
    $data = array();
    $bahan = array();
    $ctr = 0;
    while ($row = mysqli_fetch_array($result)) {
        $data["bahan_nama"] = $row["bahan_nama"];
        $data["qty"] = $row["qty"];
        $bahan[$ctr] = $data;
        $ctr++;
    }
    $response["bahanbahan"] = $bahan;
    echo json_encode($response);
}

function getbahanadmin($conn)
{
    $sql = "SELECT * FROM bahans";
    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $bahan = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["id_bahan"] = $row["bahan_id"];
            $data["bahan_nama"] = $row["bahan_nama"];
            $data["statusbahan"] = $row["bahan_isapproved"];
            $bahan[$ctr] = $data;
            $ctr++;
        }
        $response["code"] = 1;
        $response["message"] = "Request Bahan Sukses";
        $response["bahan"] = $bahan;
    } else {
        $response["code"] = -1;
        $response["message"] = "Tidak Ada Request Bahan";
    }
    echo json_encode($response);
}

function accbahanadmin($conn)
{
    $id = $_POST["bahan_id"];
    $sql = "UPDATE bahans SET bahan_isapproved = 1 WHERE bahan_id = $id";
    $result = mysqli_query($conn, $sql);

    $iyah = mysqli_query($conn, "SELECT r.resep_id, r.resep_nama,r.user_id FROM bahanresep br,reseps r WHERE br.bahan_id = $id AND br.resep_id = r.resep_id");
    while ($row = mysqli_fetch_array($iyah)) {
        $daftar[] =  (int)$row[0];
        $nama[] = strval($row[1]);
        $iduser[] =  (int)$row[2];
    }

    if (isset($daftar)) {
        for ($i = 0; $i < count($daftar); $i++) {
            $avg = mysqli_fetch_array(mysqli_query($conn, "SELECT AVG(bahan_isapproved) from bahanresep br,bahans b where br.resep_id = $daftar[$i] AND br.bahan_id = b.bahan_id"))[0];
            if ($avg == 1) {
                mysqli_query($conn, "UPDATE reseps SET resep_isapproved = 1 WHERE resep_id = $daftar[$i]");
                mysqli_query($conn, "INSERT INTO user_message (user_message,user_from, user_to,message_time) VALUES ('Menu " . $nama[$i] . " anda telah diapprove dan ditampilkan',0, $iduser[$i],'" . date("Y-m-d H:i:s") . "')");
            }
        }
    }

    if ($result) {
        $response["code"] = 1;
        $response["message"] = "Bahan Berhasil Ditambahkan";
    } else {
        $response["code"] = -1;
        $response["message"] = "Bahan Gagal Ditambahkan";
    }

    echo json_encode($response);
}

function getmess($conn)
{
    $id = $_POST["id"];
    $sql = "SELECT * FROM user_message WHERE user_to = $id";
    $result = mysqli_query($conn, $sql);

    while ($row = mysqli_fetch_array($result)) {
        $data["secs"] = strtotime(date("Y-m-d H:i:s")) - strtotime($row["message_time"]);
        if ($row["user_from"] > 0) {
            $name = mysqli_fetch_row(mysqli_query($conn, "SELECT user_viewedname FROM user WHERE user_id = " . $row["user_from"]))[0];
            $data["mess"] = $name . " berkata: " . $row["user_message"];
        } else {
            $data["mess"] = $row["user_message"];
        }
        $response[] = $data;
    }

    echo json_encode($response);
}

function delmess($conn)
{
    $id = $_POST["id"];
    mysqli_query($conn, "DELETE FROM user_message WHERE user_to = $id");
}

function havemessage($conn)
{
    $id = $_POST["id"];
    echo mysqli_fetch_row(mysqli_query($conn, "SELECT count(*) FROM user_message WHERE user_to = $id"))[0];
}

function makemessage($conn)
{
    $idasal = $_POST["idasal"];
    $idtuju = $_POST["idtuju"];
    $msg = $_POST["msg"];
    mysqli_query($conn, "INSERT INTO user_message (user_message,user_from, user_to,message_time) VALUES ('$msg',$idasal, $idtuju,'" . date("Y-m-d H:i:s") . "')");
    echo "Pesan berhasil dikirim";
}
