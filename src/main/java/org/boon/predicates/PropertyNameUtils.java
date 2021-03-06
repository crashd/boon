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

package org.boon.predicates;

import org.boon.Str;
import org.boon.core.Function;

public class PropertyNameUtils {


    public static Function<String, String> underBarCase = new Function<String, String>() {
        @Override
        public String apply( String in ) {
            return Str.underBarCase( in );
        }
    };
    public static Function<String, String> camelCase = new Function<String, String>() {
        @Override
        public String apply( String in ) {
            return Str.camelCase( in );
        }
    };


    public static Function<String, String> camelCaseUpper = new Function<String, String>() {
        @Override
        public String apply( String in ) {
            return Str.camelCaseUpper( in );
        }
    };


    public static Function<String, String> camelCaseLower = new Function<String, String>() {
        @Override
        public String apply( String in ) {
            return Str.camelCaseLower( in );
        }
    };

    public static Function<String, String> upperCase = new Function<String, String>() {
        @Override
        public String apply( String in ) {
            return in.toUpperCase();
        }
    };

    public static Function<String, String> lowerCase = new Function<String, String>() {
        @Override
        public String apply( String in ) {
            return in.toLowerCase();
        }
    };

}
