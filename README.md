# net.xelphene.cmdrun

Classes to execute a new process producing line-oriented output (like an
ordinary shell command) in a deadlock-proof manner.  The child's stdout and
stderr and both read independently by different threads and supplied to the
consuming code individually.

# Copyright and License

Copyright (C) 2016 Steve Benson

cmdrun was written by Steve Benson.

cmdrun is free software; you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free
Software Foundation; either version 3, or (at your option) any later
version.

cmdrun is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
details.

You should have received a copy of the GNU General Public License along with
this program; see the file LICENSE.  If not, see <http://www.gnu.org/licenses/>.
