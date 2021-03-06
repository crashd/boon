/*
 * Copyright 2013-2014 Richard M. Hightower
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * __________                              _____          __   .__
 * \______   \ ____   ____   ____   /\    /     \ _____  |  | _|__| ____    ____
 *  |    |  _//  _ \ /  _ \ /    \  \/   /  \ /  \\__  \ |  |/ /  |/    \  / ___\
 *  |    |   (  <_> |  <_> )   |  \ /\  /    Y    \/ __ \|    <|  |   |  \/ /_/  >
 *  |______  /\____/ \____/|___|  / \/  \____|__  (____  /__|_ \__|___|  /\___  /
 *         \/                   \/              \/     \/     \/       \//_____/
 *      ____.                     ___________   _____    ______________.___.
 *     |    |____ ___  _______    \_   _____/  /  _  \  /   _____/\__  |   |
 *     |    \__  \\  \/ /\__  \    |    __)_  /  /_\  \ \_____  \  /   |   |
 * /\__|    |/ __ \\   /  / __ \_  |        \/    |    \/        \ \____   |
 * \________(____  /\_/  (____  / /_______  /\____|__  /_______  / / ______|
 *               \/           \/          \/         \/        \/  \/
 */

package org.boon.primitive;

import org.boon.Exceptions;
import org.boon.Universal;
import org.boon.core.reflection.Invoker;

import java.lang.invoke.MethodHandle;

import static org.boon.Exceptions.die;
import static org.boon.Exceptions.handle;


public class Int {


    public static int[] grow( int[] array, final int size ) {
        Exceptions.requireNonNull( array );

        int[] newArray = new int[ array.length + size ];
        System.arraycopy( array, 0, newArray, 0, array.length );
        return newArray;
    }


    public static int[] grow( int[] array ) {
        Exceptions.requireNonNull( array );

        int[] newArray = new int[ array.length * 2 ];
        System.arraycopy( array, 0, newArray, 0, array.length );
        return newArray;
    }


    public static int[] shrink( int[] array, int size ) {
        Exceptions.requireNonNull( array );

        int[] newArray = new int[ array.length - size ];

        System.arraycopy( array, 0, newArray, 0, array.length - size );
        return newArray;
    }


    public static int[] compact( int[] array ) {
        Exceptions.requireNonNull( array );

        int nullCount = 0;
        for ( int ch : array ) {

            if ( ch == '\0' ) {
                nullCount++;
            }
        }
        int[] newArray = new int[ array.length - nullCount ];

        int j = 0;
        for ( int ch : array ) {

            if ( ch == '\0' ) {
                continue;
            }

            newArray[ j ] = ch;
            j++;
        }
        return newArray;
    }


    /**
     * Creates an array of bytes
     *
     * @param size size of the array you want to make
     * @return
     */
    public static int[] arrayOfInt( final int size ) {
        return new int[ size ];
    }

    /**
     * @param array
     * @return
     */
    @Universal
    public static int[] array( final int... array ) {
        Exceptions.requireNonNull( array );
        return array;
    }


    @Universal
    public static int lengthOf( int[] array ) {
        return len(array);
    }

    @Universal
    public static int len( int[] array ) {
        return array.length;
    }


    @Universal
    public static int idx( final int[] array, final int index ) {
        final int i = calculateIndex( array, index );

        return array[ i ];
    }


    @Universal
    public static int atIndex( final int[] array, final int index ) {
        final int i = calculateIndex( array, index );

        return array[ i ];
    }


    @Universal
    public static void idx( final int[] array, int index, int value ) {
        final int i = calculateIndex( array, index );

        array[ i ] = value;
    }

    @Universal
    public static void atIndex( final int[] array, int index, int value ) {
        final int i = calculateIndex( array, index );

        array[ i ] = value;
    }

    @Universal
    public static int[] slc( int[] array, int startIndex, int endIndex ) {

        final int start = calculateIndex( array, startIndex );
        final int end = calculateIndex( array, endIndex );
        final int newLength = end - start;

        if ( newLength < 0 ) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format( "start index %d, end index %d, length %d",
                            startIndex, endIndex, array.length )
            );
        }

        int[] newArray = new int[ newLength ];
        System.arraycopy( array, start, newArray, 0, newLength );
        return newArray;
    }

    @Universal
    public static int[] sliceOf( int[] array, int startIndex, int endIndex ) {

        final int start = calculateIndex( array, startIndex );
        final int end = calculateIndex( array, endIndex );
        final int newLength = end - start;

        if ( newLength < 0 ) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format( "start index %d, end index %d, length %d",
                            startIndex, endIndex, array.length )
            );
        }

        int[] newArray = new int[ newLength ];
        System.arraycopy( array, start, newArray, 0, newLength );
        return newArray;
    }

    @Universal
    public static int[] slc( int[] array, int startIndex ) {

        final int start = calculateIndex( array, startIndex );
        final int newLength = array.length - start;

        if ( newLength < 0 ) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format( "start index %d, length %d",
                            startIndex, array.length )
            );
        }

        int[] newArray = new int[ newLength ];
        System.arraycopy( array, start, newArray, 0, newLength );
        return newArray;
    }

    @Universal
    public static int[] sliceOf( int[] array, int startIndex ) {

        final int start = calculateIndex( array, startIndex );
        final int newLength = array.length - start;

        if ( newLength < 0 ) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format( "start index %d, length %d",
                            startIndex, array.length )
            );
        }

        int[] newArray = new int[ newLength ];
        System.arraycopy( array, start, newArray, 0, newLength );
        return newArray;
    }

    @Universal
    public static int[] slcEnd( int[] array, int endIndex ) {

        final int end = calculateIndex( array, endIndex );
        final int newLength = end; // +    (endIndex < 0 ? 1 : 0);

        if ( newLength < 0 ) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format( "start index %d, length %d",
                            endIndex, array.length )
            );
        }

        int[] newArray = new int[ newLength ];
        System.arraycopy( array, 0, newArray, 0, newLength );
        return newArray;
    }

    @Universal
    public static int[] endSliceOf( int[] array, int endIndex ) {

        final int end = calculateIndex( array, endIndex );
        final int newLength = end; // +    (endIndex < 0 ? 1 : 0);

        if ( newLength < 0 ) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format( "start index %d, length %d",
                            endIndex, array.length )
            );
        }

        int[] newArray = new int[ newLength ];
        System.arraycopy( array, 0, newArray, 0, newLength );
        return newArray;
    }

    @Universal
    public static boolean in( int value, int[] array ) {
        for ( int currentValue : array ) {
            if ( currentValue == value ) {
                return true;
            }
        }
        return false;
    }


    @Universal
    public static int[] copy( int[] array ) {
        int[] newArray = new int[ array.length ];
        System.arraycopy( array, 0, newArray, 0, array.length );
        return newArray;
    }


    @Universal
    public static int[] add( int[] array, int v ) {
        int[] newArray = new int[ array.length + 1 ];
        System.arraycopy( array, 0, newArray, 0, array.length );
        newArray[ array.length ] = v;
        return newArray;
    }

    @Universal
    public static int[] add( int[] array, int[] array2 ) {
        int[] newArray = new int[ array.length + array2.length ];
        System.arraycopy( array, 0, newArray, 0, array.length );
        System.arraycopy( array2, 0, newArray, array.length, array2.length );
        return newArray;
    }


    @Universal
    public static int[] insert( final int[] array, final int idx, final int v ) {

        if ( idx >= array.length ) {
            return add( array, v );
        }

        final int index = calculateIndex( array, idx );

        //Object newArray = Array.newInstance(array.getClass().getComponentType(), array.length+1);
        int[] newArray = new int[ array.length + 1 ];

        if ( index != 0 ) {
            /* Copy up to the length in the array before the index. */
            /*                 src     sbegin  dst       dbegin   length of copy */
            System.arraycopy( array, 0, newArray, 0, index );
        }


        boolean lastIndex = index == array.length - 1;
        int remainingIndex = array.length - index;

        if ( lastIndex ) {
            /* Copy the area after the insert. Make sure we don't write over the end. */
            /*                 src  sbegin   dst       dbegin     length of copy */
            System.arraycopy( array, index, newArray, index + 1, remainingIndex );

        } else {
            /* Copy the area after the insert.  */
            /*                 src  sbegin   dst       dbegin     length of copy */
            System.arraycopy( array, index, newArray, index + 1, remainingIndex );

        }

        newArray[ index ] = v;
        return newArray;
    }


    @Universal
    public static int[] insert( final int[] array, final int fromIndex, final int[] values ) {

        if ( fromIndex >= array.length ) {
            return add( array, values );
        }

        final int index = calculateIndex( array, fromIndex );

        //Object newArray = Array.newInstance(array.getClass().getComponentType(), array.length+1);
        int[] newArray = new int[ array.length + values.length ];

        if ( index != 0 ) {
            /* Copy up to the length in the array before the index. */
            /*                 src     sbegin  dst       dbegin   length of copy */
            System.arraycopy( array, 0, newArray, 0, index );
        }


        boolean lastIndex = index == array.length - 1;

        int toIndex = index + values.length;
        int remainingIndex = newArray.length - toIndex;

        if ( lastIndex ) {
            /* Copy the area after the insert. Make sure we don't write over the end. */
            /*                 src  sbegin   dst       dbegin     length of copy */
            System.arraycopy( array, index, newArray, index + values.length, remainingIndex );

        } else {
            /* Copy the area after the insert.  */
            /*                 src  sbegin   dst       dbegin     length of copy */
            System.arraycopy( array, index, newArray, index + values.length, remainingIndex );

        }

        for ( int i = index, j = 0; i < toIndex; i++, j++ ) {
            newArray[ i ] = values[ j ];
        }
        return newArray;
    }


    /* End universal methods. */
    private static int calculateIndex( int[] array, int originalIndex ) {
        final int length = array.length;

        Exceptions.requireNonNull( array, "array cannot be null" );


        int index = originalIndex;

        /* Adjust for reading from the right as in
        -1 reads the 4th element if the length is 5
         */
        if ( index < 0 ) {
            index = length + index;
        }

        /* Bounds check
            if it is still less than 0, then they
            have an negative index that is greater than length
         */
         /* Bounds check
            if it is still less than 0, then they
            have an negative index that is greater than length
         */
        if ( index < 0 ) {
            index = 0;
        }
        if ( index >= length ) {
            index = length - 1;
        }
        return index;
    }



    public static long reduceBy( final int[] array, Object object ) {

        MethodHandle methodHandle = Invoker.invokeReducerLongIntReturnLongMethodHandle(object);


        long sum = 0;
        for ( int v : array ) {
            try {
                sum = (long) methodHandle.invokeExact( sum, v );
            } catch (Throwable throwable) {
                handle(throwable, "Unable to perform reduceBy");
            }

        }
        return sum;
    }

    public static interface ReduceBy {
        long reduce(long sum, int value);
    }

    public static long reduceBy( final int[] array, ReduceBy reduceBy ) {


        long sum = 0;
        for ( int v : array ) {
                sum = reduceBy.reduce(sum, v);
        }
        return sum;
    }

    public static long reduceBy( final int[] array, Object object, String methodName ) {

        MethodHandle methodHandle = Invoker.invokeReducerLongIntReturnLongMethodHandle(object, methodName);


        long sum = 0;
        for ( int v : array ) {
            try {
                sum = (long) methodHandle.invokeExact( sum, v );
            } catch (Throwable throwable) {
                handle(throwable, "Unable to perform reduceBy");
            }

        }
        return sum;
    }

    public static long reduceBy( final int[] array,  int length,
                                 Object object ) {
        return reduceBy(array, 0, length, object);

    }
    public static long reduceBy( final int[] array, int start, int length,
                                 Object object ) {

        long sum = 0;
        for ( int index = start; index < length; index++ ) {
            int v = array [ index ];
            sum = (long)Invoker.invokeReducer(object, sum, v);
        }
        return sum;
    }

    public static boolean equalsOrDie(int expected, int got) {
        if (expected != got) {
            return die(Boolean.class, "Expected was", expected, "but we got ", got);
        }
        return true;
    }


    public static boolean equals(int expected, int got) {

        return expected == got;
    }


    /**
     * Sum
     * Provides overflow protection.
     * @param values values in int
     * @return sum
     */
    public static int sum( int[] values ) {
        return sum( values, 0, values.length);
    }


    /**
     * Sum
     * Provides overflow protection.
     * @param values values in int
     * @return sum
     */
    public static int sum( int[] values,  int length ) {
        return sum( values, 0, length);
    }

    /**
     * Sum
     * Provides overflow protection.
     * @param values values in int
     * @return sum
     */
    public static int sum( int[] values, int start, int length ) {
        long sum = 0;
        for (int index = start; index < length; index++ ) {
            sum+= values[index];
        }

        if (sum < Integer.MIN_VALUE) {
            die ("overflow the sum is too small", sum);
        }


        if (sum > Integer.MAX_VALUE) {
            die ("overflow the sum is too big", sum);
        }

        return (int) sum;


    }

}
