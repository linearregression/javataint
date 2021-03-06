/*
 *  Copyright 2009-2012 Michael Dalton
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package jtaint;

public final class Policy 
{
        private boolean enabled = true;
        private boolean logAttack = true;
        private boolean logVuln = true;

        public boolean getEnabled() { return enabled; }
        public void setEnabled(boolean b) { enabled = b; }

        public boolean getLogAttack() { return logAttack; }
        public void setLogAttack(boolean b) { logAttack = b; }

        public boolean getLogVuln() { return logVuln; }
        public void setLogVuln(boolean b) { logVuln = b; }
}
