package com.dhc.library.data.net.fy;

import com.dhc.library.data.net.ApiResponse;
import com.google.gson.annotations.SerializedName;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/9/22 17:22
 * 描述	     统一返回数据模型
 */

public class FyResponse<T> extends ApiResponse {
    private String state;
    private String message;
    private T data;
    private String type;
    private AttributesEntity attributes;
    private String debug;
    private String link;
    @SerializedName("error_code")
    public String error_code;

    public FyResponse() {
    }

    /**
     * 这里是相当于clone 除了data属性之外的
     * @param response
     */
    public FyResponse(FyResponse response) {
        this.state = response.state;
        this.message = response.message;
        this.type = response.type;
        this.attributes = response.attributes;
        this.debug = response.debug;
        this.link = response.link;
        this.error_code = response.error_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AttributesEntity getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesEntity attributes) {
        this.attributes = attributes;
    }

    public static class AttributesEntity {
        /**
         * number : 1
         * size : 1
         * total : 0
         * pages : 3
         * totalPrice : 6469.4
         * profit_loss: -60.12
         */

        private int number;
        private int size;
        private int total;
        private int pages;
        private double totalPrice;
        private double profit_loss;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getProfit_loss() {
            return profit_loss;
        }

        public void setProfit_loss(double profit_loss) {
            this.profit_loss = profit_loss;
        }
    }
}
