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

package org.boon.cache;

import java.util.concurrent.atomic.AtomicInteger;

import static org.boon.Exceptions.die;

class CacheEntry<KEY, VALUE> implements Comparable<CacheEntry> {
    final AtomicInteger readCount = new AtomicInteger();
    final int order;
    VALUE value;
    final KEY key;
    final CacheType type;
    final long time;


    CacheEntry( KEY key, VALUE value, int order, CacheType type, long time ) {
        this.order = order;
        this.value = value;
        this.key = key;
        this.time = time;
        this.type = type;

    }

    @Override
    public final int compareTo( CacheEntry other ) {
        switch ( type ) {

            case LFU:
                return compareToLFU( other );
            case LRU:
                return compareToLRU( other );
            case FIFO:
                return compareToFIFO( other );
            default:
                die();
                return 0;

        }
    }


    private final int compareReadCount( CacheEntry other ) {

        if ( readCount.get() > other.readCount.get() ) {  //this read count is greater so it has higher priority
            return 1;
        } else if ( readCount.get() < other.readCount.get() ) {//this read count is lower so it has lower priority
            return -1;
        } else if ( readCount.get() == other.readCount.get() ) {
            return 0;
        }
        die();
        return 0;
    }

    private final int compareTime( CacheEntry other ) {

        if ( time > other.time ) {  //this time stamp is  greater so it has higher priority
            return 1;
        } else if ( time < other.time ) {//this time stamp is lower so it has lower priority
            return -1;
        } else if ( time == other.time ) {//equal priority
            return 0;
        }
        die();
        return 0;
    }

    private final int compareOrder( CacheEntry other ) {

        if ( order > other.order ) {  //this order is lower so it has higher priority
            return 1;
        } else if ( order < other.order ) {//this order is higher so it has lower priority
            return -1;
        } else if ( order == other.order ) {//equal priority
            return 0;
        }
        die();
        return 0;
    }

    private final int compareToLFU( CacheEntry other ) {

        int cmp = compareReadCount( other );
        if ( cmp != 0 ) {
            return cmp;
        }

        cmp = compareTime( other );
        if ( cmp != 0 ) {
            return cmp;
        }

        return cmp = compareOrder( other );
    }


    private final int compareToLRU( CacheEntry other ) {

        int cmp = compareTime( other );
        if ( cmp != 0 ) {
            return cmp;
        }


        cmp = compareOrder( other );
        if ( cmp != 0 ) {
            return cmp;
        }


        return cmp = compareReadCount( other );
    }


    private final int compareToFIFO( CacheEntry other ) {
        int cmp = compareOrder( other );
        if ( cmp != 0 ) {
            return cmp;
        }


        cmp = compareTime( other );
        if ( cmp != 0 ) {
            return cmp;
        }


        return cmp = compareReadCount( other );
    }

    @Override
    public String toString() {
        return "CE{" +
                "c=" + readCount +
                ", ord=" + order +
                ", val=" + value +
                ", ky=" + key +
                ", typ=" + type +
                ", t=" + time +
                '}';
    }
}
