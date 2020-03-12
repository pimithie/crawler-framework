package com.xiaqi.crawl.filter.fetch;

import com.xiaqi.crawl.constant.MimeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FetchMetaData {

    /**
     * the file size
     */
    private long size;

    private String filename;

    private String url;

    private MimeType mimeType;

}
