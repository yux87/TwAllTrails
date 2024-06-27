package tw.team1.mall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

//接收前端傳過來的json object
public class CreateOrderRequest {
    @NotEmpty
    private List<BuyItem> buyItemList;

    private DeliveryInfo deliveryInfo;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }


    public static class DeliveryInfo {
        // ... (保持原有的屬性和方法不變) ...

        private String name;
        private String address;
        private String phone;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


}
