package com.gk.university.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HotTagDTO implements Comparable<HotTagDTO> {
    private String tag;
    private Integer count;


    @Override
    public int compareTo(@NotNull HotTagDTO o) {
        if (this.getCount() - o.getCount() == 0) {
            return this.getTag().length() - o.getTag().length();
        }
        return this.getCount() - o.getCount();
    }
}
