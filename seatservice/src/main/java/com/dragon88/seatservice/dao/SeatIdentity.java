package com.dragon88.seatservice.dao;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SeatIdentity  implements Serializable {

    private int indexRow;

    private int indexColumn;

    public SeatIdentity() {

    }

    public int getIndexRow() {
        return indexRow;
    }

    public void setIndexRow(int indexRow) {
        this.indexRow = indexRow;
    }

    public int getIndexColumn() {
        return indexColumn;
    }

    public void setIndexColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatIdentity that = (SeatIdentity) o;
        return (indexColumn == that.indexColumn && indexRow == that.indexRow);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + indexRow + indexColumn;
        return result;
    }
}
