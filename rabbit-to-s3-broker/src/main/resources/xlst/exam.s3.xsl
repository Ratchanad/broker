<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version = "1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wts="http://SENDGRDLLibrary">
    <xsl:output method="text" encoding="UTF-8" />

    <xsl:template match="/">
        <!-- Header Line -->
        <xsl:for-each select="wts:grsap/data_content/header">
            <xsl:text>H|</xsl:text>
            <xsl:value-of select="store_id"/><xsl:text>|</xsl:text>
            <xsl:value-of select="translate(return_date,'-','')"/><xsl:text>|</xsl:text>
            <xsl:value-of select="return_no"/><xsl:text>|</xsl:text>
            <xsl:value-of select="warehouse_code"/><xsl:text>|</xsl:text>
            <xsl:value-of select="reference_no"/><xsl:text>|</xsl:text>
            <xsl:value-of select="store_type"/><xsl:text>|</xsl:text>
            <xsl:value-of select="normalize-space(area_code)"/><xsl:text>|</xsl:text>
            <xsl:value-of select="approve_flag"/><xsl:text>|</xsl:text>
            <xsl:value-of select="translate(approve_date,'-','')"/><xsl:text>|</xsl:text>
            <xsl:value-of select="dc_no"/><xsl:text>|</xsl:text>
            <xsl:value-of select="dc_no"/><xsl:text>|</xsl:text>
            <xsl:value-of select="translate(cn_date,'-','')"/><xsl:text>|</xsl:text>
            <xsl:value-of select="price_amt"/><xsl:text>|</xsl:text>
            <xsl:value-of select="retail_amt"/><xsl:text>|</xsl:text>
            <xsl:value-of select="retail_vat"/><xsl:text>|</xsl:text>
            <xsl:value-of select="total_detail_record"/><xsl:text>|</xsl:text>
            <xsl:value-of select="total_approve_record"/><xsl:text>|</xsl:text>
            <xsl:choose>
                <xsl:when test="generate_flag='G'"><xsl:text>|</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="generate_flag"/><xsl:text>|</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:value-of select="return_no"/><xsl:text>|</xsl:text>
            <xsl:text>&#10;</xsl:text>
        </xsl:for-each>

        <!-- Detail Lines -->
        <xsl:for-each select="wts:grsap/data_content/detail">
            <xsl:text>D|</xsl:text>
            <xsl:value-of select="../header/store_id"/><xsl:text>|</xsl:text>
            <xsl:value-of select="translate(../header/return_date,'-','')"/><xsl:text>|</xsl:text>
            <xsl:value-of select="../header/return_no"/><xsl:text>|</xsl:text>
            <xsl:value-of select="extend_no"/><xsl:text>|</xsl:text>
            <xsl:value-of select="../header/store_type"/><xsl:text>|</xsl:text>
            <xsl:value-of select="normalize-space(../header/area_code)"/><xsl:text>|</xsl:text>
            <xsl:value-of select="reason_code"/><xsl:text>|</xsl:text>
            <xsl:value-of select="normalize-space(product_code)"/><xsl:text>|</xsl:text>
            <xsl:value-of select="return_qty"/><xsl:text>|</xsl:text>
            <xsl:value-of select="approve_qty"/><xsl:text>|</xsl:text>
            <xsl:value-of select="price_amt"/><xsl:text>|</xsl:text>
            <xsl:value-of select="total_rtl"/><xsl:text>|</xsl:text>
            <xsl:value-of select="cost_amt"/><xsl:text>|</xsl:text>
            <xsl:text>&#10;</xsl:text>
        </xsl:for-each>
    </xsl:template>
</xsl:transform>
