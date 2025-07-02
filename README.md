<!--///////////////////////////////////////////////////////////////////////-->
<h1>ỨNG DỤNG BÁN HÀNG ĐIỆN TỬ (trên mobile)</h1>
<p><i>(Ứng dụng này là đề tài thực hành môn Project 3)</i></p></br>
<h2>1. TỔNG QUAN</h2>
Các chức năng chính:</br>
<ul>
  <li>Đăng ký/ Đăng nhập tài khoản người dùng</li>
  <li>Đăng nhập bằng tài khoản Google</li>
  <li>Dữ liệu người dùng (tài khoản, mật khẩu, tên) lưu vào Firebase Authentication</li>
  <li>Hiển thị sản phẩm theo danh sách cuộn(RecyclerView)</li> 
  <li>Dữ liệu sản phẩm lưu vào Firebase Firestore</li>
  <li>Tìm kiếm sản phẩm</li>
  <li>Xem chi tiết từng sản phẩm</li>
  <li>Thêm sản phẩm vào giỏ hàng theo danh sách cuộn(RecyclerView)</li>
  <li>Xóa, tăng/giảm số lượng sản phẩm trong giỏ hàng</li>
  <li>Thanh toán, giao hàng</li>
  <li>Xem thông tin cá nhân (Tên, Email, UID) và sửa (Tên profile)</li>
  <li>Theo dõi đơn hàng đã đặt trong Profile</li>
</ul>
<h2>2. DEMO OUTPUT</h2>
<table>
  <tbody>
    <tr>
      <td align="center">Register</td>
      <td align="center">Login</td>
      <td align="center">Login with Google account</td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/3f505f6b-1b11-4474-8ece-c58c54adfbc8" width="250" alt="Register">
      </td>
      <td>
        <img src="https://github.com/user-attachments/assets/b81d1abb-288f-4dc5-ae6b-d59daf020d98" width="250" alt="Login">
      </td>
      <td>
        <img src="https://github.com/user-attachments/assets/0ba8ed64-464a-423c-9778-a92c4f36919a" width="250" alt="Product list">
      </td>
    </tr>
  </tbody>
</table>

<table>
  <tbody>
     <tr>
      <td align="center">Product List</td>
      <td align="center">Search</td>
      <td align="center">Shopping Cart</td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/80a25abd-5630-431f-83cf-939f51f9bc60" width="250" alt="Search">
      </td>
      <td>
        <img src="https://github.com/user-attachments/assets/2f288e6c-3094-462b-95e9-bf97e358b024" width="250" alt="Shopping cart">
      </td>
      <td>
        <img src="https://github.com/user-attachments/assets/5c22c01e-f6e4-43ca-a245-34b68e24602d" width="250" alt="Cart payment">
      </td>
    </tr> 
  </tbody>
</table>
    
<table>
  <tbody>
    <tr>
      <td align="center">Delivery info</td>
      <td align="center">Cart payment</td>
      <td align="center">Profile Order</td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/d19ac55b-4226-41cc-a9fa-00296c5c1285" width="250" alt="Profile">
      </td>
      <td>
        <img src="https://github.com/user-attachments/assets/bdd5e78f-f401-47a3-af33-1063b0d86178" width="250" alt="Edited profile">
      </td>
      <td>
        <img src="https://github.com/user-attachments/assets/beaee007-9170-412e-8676-fe7efa0eb4d6" width="250" alt="Logout dialog">
      </td>
    </tr>
  </tbody>
</table>

<table>
  <tbody>
    <tr>
      <td align="center">Profile name edit dialog</td>
      <td align="center">Edited profile</td>
      <td align="center">Logout</td>
    </tr>
    <tr>
      <td>
        <img src="https://github.com/user-attachments/assets/13d5b0c2-130b-4963-89fa-faea66c38d2a" width="250" alt="Logout dialog">
      </td>
       <td>
        <img src="https://github.com/user-attachments/assets/fec81a50-9ad2-4144-bc44-161218579e73" width="250" alt="Edited profile">
      </td>
       <td>
        <img src="https://github.com/user-attachments/assets/ba3ce811-ff18-49c6-bee3-0dae69ce3944" width="250" alt="Edited profile">
      </td>
    </tr>
  </tbody>
</table>

<h2>3. CÁCH SỬ DỤNG (đối với Desktop/Laptop đã cài Android Studio và máy điện thoại có hệ điều hành Android đã bật CHẾ ĐỘ DÀNH CHO NHÀ PHÁT TRIỂN)</h2>
<p><i>If you know, you know...😏</i></p>
