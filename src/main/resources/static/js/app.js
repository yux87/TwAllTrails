import { uploadImageToFirebase } from "./firebase.js";

const { createApp, ref, onMounted } = Vue;

const app = createApp({
  setup() {
    const searchQuery = ref({
      category: "",
      search: "",
    });

    const selectedProduct = ref(null);
    const quantity = ref(1); // 新增的商品數量

    const fetchProductDetails = async (productId) => {
      const response = await axios.get(
          `/products/${productId}`
      );
      selectedProduct.value = response.data;
    };

    const products = ref([]);
    const form = ref({
      productName: "",
      category: "",
      price: 0,
      stock: 0,
    });

    const file = ref(null);
    const isEditing = ref(false);
    const editingId = ref(null);

    const fetchProducts = async () => {
      try {
        const params = new URLSearchParams({
          category: searchQuery.value.category,
          search: searchQuery.value.search,
          orderBy: "created_date",
          sort: "desc",
          limit: 50,
          offset: 0,
        }).toString();
        const response = await axios.get(
            `/products?${params}`
        );
        products.value = response.data;
      } catch (error) {
        console.error(error);
      }
    };

    const deleteProduct = async (id) => {
      try {
        await axios.delete(`/products/${id}`);
        fetchProducts();
      } catch (error) {
        console.error(error);
      }
    };

    const editProduct = (product) => {
      form.value = { ...product };
      isEditing.value = true;
      editingId.value = product.productId;
    };

    const handleFileChange = (event) => {
      file.value = event.target.files[0];
    };

    // 提交產品表單
    const submitForm = async () => {
      if (file.value) {
        try {
          const imageUrl = await uploadImageToFirebase(file.value);
          form.value.imageUrl = imageUrl;
        } catch (error) {
          console.error("Error uploading image:", error);
          return;
        }
      }

      try {
        if (isEditing.value) {
          await axios.put(
              `/products/${editingId.value}`,
              form.value
          );
        } else {
          await axios.post("/products", form.value);
        }
        fetchProducts();
        form.value = {
          productName: "",
          category: "",
          price: 0,
          stock: 0,
        };
        isEditing.value = false;
        editingId.value = null;
        file.value = null;
      } catch (error) {
        console.error(error);
      }
    };
    // 添加產品到購物車
    const addToCart = (product, quantity) => {
      let cart = JSON.parse(localStorage.getItem("cart")) || [];
      const existingProduct = cart.find(
          (item) => item.productId === product.productId
      );
      if (existingProduct) {
        existingProduct.quantity += quantity;
      } else {
        cart.push({ ...product, quantity: quantity });
      }
      localStorage.setItem("cart", JSON.stringify(cart));
      alert("商品已加入購物車");
    };

    // 訂單資料的反應性引用
    const orders = ref([]);



    // 從後端獲取訂單資料
    // TODO: 將來需要更新這個函數來使用實際的使用者 ID，
    // 可能來自 Vue 的反應性引用或某種全局狀態管理 (例如 Vuex)
    const fetchOrders = async (userId) => {
      try {
        const response = await axios.get(`/users/${userId}/orders`);
        orders.value = response.data.results;
      } catch (error) {
        console.error(error);
      }
    };

    // 在組件掛載時獲取產品列表和訂單
    onMounted(() => {
      fetchProducts();

      // 從登入狀態獲取真實用戶ID
      axios.get('/getUserProfiles')
          .then(response => {
            const member = response.data;
            const memberid = member.memberid;
            console.log('獲取會員信息成功,會員id為:', memberid);

            // 使用從登入狀態獲得的真實用戶ID來獲取訂單
            fetchOrders(memberid);
          })
          .catch(error => {
            console.error('獲取會員信息失敗:', error);
          });
    });

    return {
      products,
      deleteProduct,
      editProduct,
      submitForm,
      form,
      handleFileChange,
      isEditing,
      searchQuery,
      fetchProducts,
      selectedProduct,
      fetchProductDetails,
      addToCart,
      quantity, // 新增的商品數量
      orders, // 新增的訂單列表
      fetchOrders, // 新增的函數
    };
  },
});

app.mount("#app");
