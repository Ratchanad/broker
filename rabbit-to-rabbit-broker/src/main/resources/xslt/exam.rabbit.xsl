<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wts="http://WTSSendLibrary">
    <xsl:output method="text" encoding="UTF-8" />

    <xsl:template match="/">
        <xsl:for-each select="data_content/detail">
            <xsl:value-of select="../header/warehouse_code"/> <xsl:text>|</xsl:text>
            <xsl:value-of select="../header/input_doc_no"/> <xsl:text>|</xsl:text>
            <xsl:value-of select="product_code"/> <xsl:text>|</xsl:text>
            <xsl:value-of select="total_qty"/>
            <xsl:text>|&#10;</xsl:text>
        </xsl:for-each>
    </xsl:template>
</xsl:transform>
