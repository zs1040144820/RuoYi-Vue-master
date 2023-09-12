package com.ruoyi.addrgen.domain;

public class AddrgenSeedfileDetailsUp {
    private Long seedId;
    private String category;

    public Long getSeedId() {
        return seedId;
    }

    public void setSeedId(Long seedId) {
        this.seedId = seedId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "AddrgenSeedfileDetailsUp{" +
                "seedId=" + seedId +
                ", category='" + category + '\'' +
                '}';
    }
}
